package command

import io.IOHandler
import manager.CollectionManager
import model.Coordinates
import model.Organization
import model.Product
import java.time.ZonedDateTime
import java.util.LinkedList
import kotlin.text.toLong
import kotlin.text.toLongOrNull

class ProductReader(
    private val io: IOHandler,
) {
    fun read(): Product {
        io.println("введите имя")
        val name = io.readLine() ?: "default"

        var price: Long?

        do {
            io.println("введите цену:")
            var input = io.readLine()

            price = input?.toLongOrNull()

            if (price == null || price <= 0) {
                io.println("цена должна быть больше 0")
                price = null
            }
        }
        while (price == null)

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
        return newProduct
    }
}
