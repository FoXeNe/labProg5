package reader

import io.IOHandler
import model.UnitOfMeasure

class UnitOfMeasureReader(
    private val io: IOHandler,
) {
    fun read(): UnitOfMeasure? {
        while (true) {
            io.println("выберите UnitOfMeasure или ничего не пишите")
            for (value in UnitOfMeasure.values()) {
                io.println(value.toString())
            }
            val input = io.readLine()
            if (input.isNullOrBlank()) return null
            try {
                return UnitOfMeasure.valueOf(input.uppercase())
            } catch (e: IllegalArgumentException) {
                io.println("ввод не является UnitOfMeasure, попробуйте еще раз")
            }
        }
    }
}
