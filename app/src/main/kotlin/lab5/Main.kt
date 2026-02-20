package lab5

import lab5.manager.CollectionManager
import lab5.model.Coordinates
import lab5.model.Organization
import lab5.model.Product
import java.time.ZonedDateTime
import java.util.LinkedList
import kotlin.text.toLong
import kotlin.text.toLongOrNull

fun main() {
    val manager = CollectionManager()

    println("введите имя")
    val name = readLine() ?: "default"

    println("введите цену")
    val price = readLine()?.toLongOrNull() ?: 0L

    val newProduct =
        Product(
            id = 1,
            name = name,
            // TODO: do this coordinates section
            coordinates = Coordinates(10L, 10F),
            creationDate = ZonedDateTime.now(),
            price = price,
            unitOfMeasure = null,
            // TODO: do this Organization section
            manufacturer = Organization(1L, "test", "testtest", 10L),
        )
    manager.addProduct(newProduct)
}
