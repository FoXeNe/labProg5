package command.commands

import command.Command
import io.IOHandler
import manager.CollectionManager

class FilterByManufacturer(
    private val io: IOHandler,
    private val collectionManager: CollectionManager,
) : Command {
    override val name = "filter_by_manufacturer"
    override val description = "display the elements whose manufacturer field value matches value"

    override fun execute(args: String) {
        val name = args.trim()
        if (name.isBlank()) {
            io.println("укажите имя производителя, например: filter_by_manufacturer NAME")
            return
        }
        val result = collectionManager.filterByManufacturer(name)
        if (result.isEmpty()) {
            io.println("элементов с таким производителем не найдено")
        } else {
            result.forEach { io.println(it.toString()) }
        }
    }
}
