package command.commands

import command.Command
import io.IOHandler
import manager.CollectionManager
import reader.ProductReader

class Update(
    private val io: IOHandler,
    private val collectionManager: CollectionManager,
) : Command {
    override val name = "update"
    override val description = "update element by id"

    override fun execute(args: String) {
        val id = args.trim().toLongOrNull()
        if (id == null) {
            io.println("введите id, к примеру: update 5")
            return
        }

        if (collectionManager.getCollection().none { it.id == id }) {
            io.println("элемент с id=$id не найден")
        }
        val newProduct = ProductReader(io).read()

        collectionManager.updateById(id, newProduct)
    }
}
