package manager

import model.Product
import java.util.LinkedList

class CollectionManager {
    private val list = LinkedList<Product>()

    fun addProduct(product: Product) {
        list.add(product)
        println(list)
    }
}
