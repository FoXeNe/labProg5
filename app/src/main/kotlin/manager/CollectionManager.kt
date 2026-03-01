package manager

import io.IOHandler
import model.Product
import java.util.LinkedList

class CollectionManager(
    private val io: IOHandler,
) {
    private val list = LinkedList<Product>()

    fun addProduct(product: Product) {
        list.add(product)
        io.println(list.toString())
    }
}
