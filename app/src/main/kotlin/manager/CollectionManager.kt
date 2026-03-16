package manager

import io.IOHandler
import model.Product
import java.time.ZonedDateTime
import java.util.LinkedList

class CollectionManager(
    private val io: IOHandler,
    collection: LinkedList<Product> = LinkedList(),
) {
    private val list = LinkedList<Product>(collection)
    private var currProductId: Long
    private var currOrgId: Long
    private val initDate: ZonedDateTime = ZonedDateTime.now()

    init {
        currProductId = if (list.isEmpty()) 1L else list.maxOf { it.id } + 1
        currOrgId = if (list.isEmpty()) 1L else list.maxOf { it.manufacturer.id } + 1
    }

    fun addProduct(product: Product) {
        list.add(generateId(product))
        io.println("продукт добавлен")
    }

    fun generateId(product: Product): Product =
        product.copy(
            id = currProductId++,
            manufacturer = product.manufacturer.copy(id = currOrgId++),
        )

    fun getInfoString(): String =
        """
        тип: ${list.javaClass.name}
        дата инициализации: $initDate
        количество элементов: ${list.size}
        """.trimIndent()

    fun updateById(
        id: Long,
        newProduct: Product,
    ) {
        val index = list.indexOfFirst { it.id == id }
        val old = list[index]
        list[index] =
            newProduct.copy(
                id = old.id,
                creationDate = old.creationDate,
                manufacturer = newProduct.manufacturer.copy(id = old.manufacturer.id),
            )
        io.println("элемент обновлён")
    }

    fun removeById(id: Long) {
        list.removeAll { it.id == id }
        io.println("элемент удалён")
    }

    fun removeFirst() {
        list.removeFirst()
        io.println("первый элемент удалён")
    }

    fun clear() {
        list.clear()
        io.println("коллекция очищена")
    }

    fun getCollection(): LinkedList<Product> = list

    fun getMinProduct(): Product? = list.minOrNull()

    fun sumOfPrice(): Long = list.sumOf { it.price }

    fun filterByManufacturer(manufacturerName: String): List<Product> {
        val result = mutableListOf<Product>()
        for (product in list) {
            if (product.manufacturer.name == manufacturerName) {
                result.add(product)
            }
        }
        return result
    }

    fun filterGreaterThanManufacturer(manufacturerName: String): List<Product> {
        val result = mutableListOf<Product>()
        for (product in list) {
            if (product.manufacturer.name > manufacturerName) {
                result.add(product)
            }
        }
        return result
    }
}
