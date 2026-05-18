package manager

import model.Coordinates
import model.Organization
import model.Product
import model.UnitOfMeasure
import java.sql.Connection
import java.sql.DriverManager
import java.time.OffsetDateTime
import java.util.logging.Logger

class DatabaseManager(
    url: String,
    user: String,
    password: String,
) {
    private val logger = Logger.getLogger(DatabaseManager::class.java.name)
    private val connection: Connection = DriverManager.getConnection(url, user, password)

    init {
        connection.autoCommit = true
        createSchema()
    }

    @Synchronized
    private fun createSchema() {
        connection.createStatement().use { stmt ->
            stmt.execute(
                """CREATE TABLE IF NOT EXISTS users (
                    login VARCHAR(255) PRIMARY KEY,
                    password_hash VARCHAR(64) NOT NULL
                )""",
            )
            stmt.execute("CREATE SEQUENCE IF NOT EXISTS product_id_seq START 1")
            stmt.execute("CREATE SEQUENCE IF NOT EXISTS org_id_seq START 1")
            stmt.execute(
                """CREATE TABLE IF NOT EXISTS products (
                    id BIGINT PRIMARY KEY DEFAULT nextval('product_id_seq'),
                    name VARCHAR(255) NOT NULL,
                    coord_x BIGINT NOT NULL,
                    coord_y REAL NOT NULL,
                    creation_date TIMESTAMPTZ NOT NULL,
                    price BIGINT NOT NULL,
                    unit_of_measure VARCHAR(50),
                    org_id BIGINT NOT NULL DEFAULT nextval('org_id_seq'),
                    org_name VARCHAR(255) NOT NULL,
                    org_full_name VARCHAR(532) NOT NULL,
                    org_employees_count BIGINT NOT NULL,
                    owner_login VARCHAR(255) NOT NULL REFERENCES users(login)
                )""",
            )
        }
        logger.info("схема БД создана")
    }

    @Synchronized
    fun registerUser(
        login: String,
        passwordHash: String,
    ): Boolean =
        try {
            connection
                .prepareStatement(
                    "INSERT INTO users (login, password_hash) VALUES (?, ?)",
                ).use { stmt ->
                    stmt.setString(1, login)
                    stmt.setString(2, passwordHash)
                    stmt.executeUpdate()
                }
            true
        } catch (e: Exception) {
            false
        }

    @Synchronized
    fun getPasswordHash(login: String): String? {
        connection.prepareStatement("SELECT password_hash FROM users WHERE login=?").use { stmt ->
            stmt.setString(1, login)
            val rs = stmt.executeQuery()
            return if (rs.next()) rs.getString("password_hash") else null
        }
    }

    @Synchronized
    fun loadAll(): List<Pair<Product, String>> {
        val result = mutableListOf<Pair<Product, String>>()
        connection.createStatement().use { stmt ->
            val rs = stmt.executeQuery("SELECT * FROM products ORDER BY id")
            while (rs.next()) {
                val product =
                    Product(
                        id = rs.getLong("id"),
                        name = rs.getString("name"),
                        coordinates =
                            Coordinates(
                                x = rs.getLong("coord_x"),
                                y = rs.getFloat("coord_y"),
                            ),
                        creationDate =
                            rs
                                .getObject("creation_date", OffsetDateTime::class.java)
                                .toZonedDateTime(),
                        price = rs.getLong("price"),
                        unitOfMeasure = rs.getString("unit_of_measure")?.let { UnitOfMeasure.valueOf(it) },
                        manufacturer =
                            Organization(
                                id = rs.getLong("org_id"),
                                name = rs.getString("org_name"),
                                fullName = rs.getString("org_full_name"),
                                employeesCount = rs.getLong("org_employees_count"),
                            ),
                    )
                result.add(product to rs.getString("owner_login"))
            }
        }
        return result
    }

    @Synchronized
    fun insertProduct(
        product: Product,
        ownerLogin: String,
    ): Product {
        val sql =
            """INSERT INTO products (name, coord_x, coord_y, creation_date, price, unit_of_measure,
                org_name, org_full_name, org_employees_count, owner_login)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id, org_id"""
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, product.name)
            stmt.setLong(2, product.coordinates.x)
            stmt.setFloat(3, product.coordinates.y)
            stmt.setObject(4, product.creationDate.toOffsetDateTime())
            stmt.setLong(5, product.price)
            stmt.setString(6, product.unitOfMeasure?.name)
            stmt.setString(7, product.manufacturer.name)
            stmt.setString(8, product.manufacturer.fullName)
            stmt.setLong(9, product.manufacturer.employeesCount)
            stmt.setString(10, ownerLogin)
            val rs = stmt.executeQuery()
            rs.next()
            return product.copy(
                id = rs.getLong("id"),
                manufacturer = product.manufacturer.copy(id = rs.getLong("org_id")),
            )
        }
    }

    @Synchronized
    fun updateProduct(
        product: Product,
        ownerLogin: String,
    ): Boolean {
        val sql =
            """UPDATE products SET name=?, coord_x=?, coord_y=?, price=?, unit_of_measure=?,
                org_name=?, org_full_name=?, org_employees_count=?
            WHERE id=? AND owner_login=?"""
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, product.name)
            stmt.setLong(2, product.coordinates.x)
            stmt.setFloat(3, product.coordinates.y)
            stmt.setLong(4, product.price)
            stmt.setString(5, product.unitOfMeasure?.name)
            stmt.setString(6, product.manufacturer.name)
            stmt.setString(7, product.manufacturer.fullName)
            stmt.setLong(8, product.manufacturer.employeesCount)
            stmt.setLong(9, product.id)
            stmt.setString(10, ownerLogin)
            return stmt.executeUpdate() > 0
        }
    }

    @Synchronized
    fun deleteProduct(
        id: Long,
        ownerLogin: String,
    ): Boolean {
        connection.prepareStatement("DELETE FROM products WHERE id=? AND owner_login=?").use { stmt ->
            stmt.setLong(1, id)
            stmt.setString(2, ownerLogin)
            return stmt.executeUpdate() > 0
        }
    }

    @Synchronized
    fun clearUserProducts(ownerLogin: String): Int {
        connection.prepareStatement("DELETE FROM products WHERE owner_login=?").use { stmt ->
            stmt.setString(1, ownerLogin)
            return stmt.executeUpdate()
        }
    }

    @Synchronized
    fun getProductOwner(id: Long): String? {
        connection.prepareStatement("SELECT owner_login FROM products WHERE id=?").use { stmt ->
            stmt.setLong(1, id)
            val rs = stmt.executeQuery()
            return if (rs.next()) rs.getString("owner_login") else null
        }
    }
}
