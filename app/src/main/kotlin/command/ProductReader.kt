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
            val input = io.readLine()

            price = input?.toLongOrNull()

            if (price == null || price <= 0) {
                io.println("цена должна быть больше 0")
                price = null
            }
        }
        while (price == null)

        // coordinates
        var coordx: Long? = null
        while (coordx == null) {
            io.println("введите x")
            val inputx = io.readLine()
            coordx = inputx?.toLongOrNull()

            if (coordx == null) io.println("x должен быть не null")
        }

        var coordy: Float? = null
        while (coordy == null) {
            io.println("введите y")
            val inputy = io.readLine()
            coordy = inputy?.toFloatOrNull()

            if (coordy == null) io.println("y должен быть не null")
        }

        val newProduct =
            Product(
                id = 1,
                name = name,
                coordinates = Coordinates(coordx, coordy),
                creationDate = ZonedDateTime.now(),
                price = price,
                unitOfMeasure = null,
                // TODO: do this Organization section
                manufacturer = Organization(1L, "test", "testtest", 10L),
            )
        return newProduct
    }
}
