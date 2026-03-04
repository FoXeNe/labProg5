package command.commands

import command.Command
import io.IOHandler
import manager.CollectionManager

class RemoveById(
    private val io: IOHandler,
    private val collectionManager: CollectionManager,
) : Command {
    override val name = "remove_by_id"
    override val description = "remove element by id"

    override fun execute(args: String) {
        val id = args.trim().toLongOrNull()
        if (id == null) {
            io.println("введите id, к примеру: remove_by_id 5")
            return
        }

        if (collectionManager.getCollection().none { it.id == id }) {
            io.println("элемент с id=$id не найден")
            return
        }

        collectionManager.removeById(id)
    }
}
