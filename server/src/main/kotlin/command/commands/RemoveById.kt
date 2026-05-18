package command.commands

import command.Command
import manager.CollectionManager
import model.CommandResult
import model.Product

class RemoveById(
    private val collectionManager: CollectionManager,
) : Command {
    override val name = "remove_by_id"
    override val description = "remove element by id"

    override fun execute(
        args: String,
        product: Product?,
        ownerLogin: String?,
    ): CommandResult {
        val owner = ownerLogin ?: return CommandResult(false, "требуется авторизация")
        val id = args.trim().toLongOrNull() ?: return CommandResult(false, "введите id, например: remove_by_id 5")
        if (!collectionManager.hasId(id)) return CommandResult(false, "элемент с id=$id не найден")
        if (collectionManager.getOwner(id) != owner) return CommandResult(false, "нельзя удалить чужой элемент")
        return if (collectionManager.removeById(id, owner)) {
            CommandResult(true, "элемент удалён")
        } else {
            CommandResult(false, "не удалось удалить элемент")
        }
    }
}
