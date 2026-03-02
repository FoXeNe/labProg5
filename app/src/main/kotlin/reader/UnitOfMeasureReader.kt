package reader

import io.IOHandler
import model.UnitOfMeasure

class UnitOfMeasureReader(
    private val io: IOHandler,
) {
    fun read(): UnitOfMeasure? {
        io.println("выберите нужную единицу измерения или ничего не пишите")
        for (values in UnitOfMeasure.values()) {
            io.println(values.toString())
        }
        val input = io.readLine()
        if (input.isNullOrBlank()) return null
        return UnitOfMeasure.valueOf(input.uppercase())
    }
}
