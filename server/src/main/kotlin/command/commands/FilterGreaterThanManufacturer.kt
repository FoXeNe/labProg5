package command.commands

import command.Command
import io.IOHandler
import manager.CollectionManager

class FilterGreaterThanManufacturer(
    private val io: IOHandler,
    private val collectionManager: CollectionManager,
) : Command {
    override val name = "filter_greater_than_manufacturer"
    override val description = "return the elements whose manufacturer field value is greater than value"

    override fun execute(args: String) {
        val name = args.trim()
        if (name.isBlank()) {
            io.println("укажите имя производителя, например: filter_greater_than_manufacturer NAME")
            return
        }
        val result = collectionManager.filterGreaterThanManufacturer(name)
        if (result.isEmpty()) {
            io.println("элементов с проивзодителем больше не найдено")
        } else {
            result.forEach { io.println(it.toString()) }
        }
    }
}
