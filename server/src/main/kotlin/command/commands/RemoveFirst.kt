package command.commands

import command.Command
import manager.CollectionManager
import model.CommandResult
import model.Product

class RemoveFirst(
    private val collectionManager: CollectionManager,
) : Command {
    override val name = "remove_first"
    override val description = "remove first element"

    override fun execute(
        args: String,
        product: Product?,
        ownerLogin: String?,
    ): CommandResult {
        val owner = ownerLogin ?: return CommandResult(false, "требуется авторизация")
        val collection = collectionManager.getCollection()
        if (collection.isEmpty()) return CommandResult(false, "коллекция пустая")
        val first = collection.first()
        if (collectionManager.getOwner(first.id) != owner) return CommandResult(false, "первый элемент принадлежит другому пользователю")
        return if (collectionManager.removeFirst(owner)) {
            CommandResult(true, "первый элемент удалён")
        } else {
            CommandResult(false, "не удалось удалить первый элемент")
        }
    }
}
