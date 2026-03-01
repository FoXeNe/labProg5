package manager

import io.IOHandler
import model.Product
import java.util.LinkedList

class CollectionManager(
    private val io: IOHandler,
) {
    private val list = LinkedList<Product>()
    private var currId = 1L

    fun addProduct(product: Product) {
        val productWithId = product.copy(id = currId)
        list.add(productWithId)
        currId++
        io.println(list.toString())
    }
}
