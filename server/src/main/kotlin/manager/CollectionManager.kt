package manager

import model.Product
import java.time.ZonedDateTime
import java.util.LinkedList
import java.util.concurrent.locks.ReentrantReadWriteLock
import java.util.stream.Collectors
import kotlin.concurrent.withLock

class CollectionManager(
    private val db: DatabaseManager,
    initialData: List<Pair<Product, String>> = emptyList(),
) {
    private val list = LinkedList<Product>()
    private val ownerMap = mutableMapOf<Long, String>()
    private val lock = ReentrantReadWriteLock()
    private val initDate: ZonedDateTime = ZonedDateTime.now()
    private var modified = false

    init {
        for ((product, owner) in initialData) {
            list.add(product)
            ownerMap[product.id] = owner
        }
    }

    fun addProduct(product: Product, ownerLogin: String) {
        val withId = db.insertProduct(product, ownerLogin)
        lock.writeLock().withLock {
            list.add(withId)
            ownerMap[withId.id] = ownerLogin
        }
    }

    fun getInfoString(): String =
        lock.readLock().withLock {
            """
            тип: ${list.javaClass.name}
            дата инициализации: $initDate
            количество элементов: ${list.size}
            """.trimIndent()
        }

    fun updateById(
        id: Long,
        newProduct: Product,
        ownerLogin: String,
    ): Boolean =
        lock.writeLock().withLock {
            val index = list.indexOfFirst { it.id == id }
            if (index < 0) return@withLock false
            if (ownerMap[id] != ownerLogin) return@withLock false
            val old = list[index]
            val updated =
                newProduct.copy(
                    id = old.id,
                    creationDate = old.creationDate,
                    manufacturer = newProduct.manufacturer.copy(id = old.manufacturer.id),
                )
            if (!db.updateProduct(updated, ownerLogin)) return@withLock false
            list[index] = updated
            true
        }

    fun removeById(
        id: Long,
        ownerLogin: String,
    ): Boolean =
        lock.writeLock().withLock {
            if (ownerMap[id] != ownerLogin) return@withLock false
            if (!db.deleteProduct(id, ownerLogin)) return@withLock false
            list.removeAll { it.id == id }
            ownerMap.remove(id)
            true
        }

    fun removeFirst(ownerLogin: String): Boolean =
        lock.writeLock().withLock {
            if (list.isEmpty()) return@withLock false
            val first = list.first()
            if (ownerMap[first.id] != ownerLogin) return@withLock false
            if (!db.deleteProduct(first.id, ownerLogin)) return@withLock false
            list.removeFirst()
            ownerMap.remove(first.id)
            true
        }

    fun clear(ownerLogin: String): Int =
        lock.writeLock().withLock {
            val count = db.clearUserProducts(ownerLogin)
            val toRemove = ownerMap.entries.filter { it.value == ownerLogin }.map { it.key }.toSet()
            list.removeAll { it.id in toRemove }
            toRemove.forEach { ownerMap.remove(it) }
            count
        }

    fun getCollection(): LinkedList<Product> =
        lock.readLock().withLock { LinkedList(list) }

    fun getMinProduct(): Product? =
        lock.readLock().withLock {
            list
                .stream()
                .min(Comparator.naturalOrder())
                .orElse(null)
        }

    fun sumOfPrice(): Long =
        lock.readLock().withLock {
            list
                .stream()
                .mapToLong { it.price }
                .sum()
        }

    fun filterByManufacturer(manufacturerName: String): List<Product> =
        lock.readLock().withLock {
            list
                .stream()
                .filter { it.manufacturer.name == manufacturerName }
                .collect(Collectors.toList())
        }

    fun filterGreaterThanManufacturer(manufacturerName: String): List<Product> =
        lock.readLock().withLock {
            list
                .stream()
                .filter { it.manufacturer.name > manufacturerName }
                .collect(Collectors.toList())
        }

    fun hasId(id: Long): Boolean =
        lock.readLock().withLock { list.any { it.id == id } }

    fun getOwner(id: Long): String? =
        lock.readLock().withLock { ownerMap[id] }
}
