package reader

import io.IOHandler
import model.Coordinates

class CoordinatesReader(
    private val io: IOHandler,
) {
    fun read(): Coordinates {
        var x: Long? = null
        var y: Float? = null
        while (x == null) {
            io.println("введите x")
            x = io.readLine()?.toLongOrNull()
            if (x !is Long) {
                io.println("x должен быть long")
                x = null
            }
        }
        while (y == null) {
            io.println("введите y")
            y = io.readLine()?.toFloatOrNull()
            if (y !is Float) {
                io.println("y должен быть float")
                y = null
            }
        }
        return Coordinates(x, y)
    }
}
