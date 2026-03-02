package reader

import io.IOHandler
import model.Coordinates

class CoordinatesReader(
    private val io: IOHandler,
) {
    fun read(): Coordinates {
        var x = io.readLong("введите x")
        var y = io.readFloat("введите y")
        return Coordinates(x, y)
    }
}
