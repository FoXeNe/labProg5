package command.commands

import command.Command
import io.IOHandler
import manager.CollectionManager
import model.CommandResult
import model.Product
import reader.ProductReader

class Update(
    private val io: IOHandler,
    private val collectionManager: CollectionManager,
) : Command {
    override val name = "update"
    override val description = "update element by id"

    override fun execute(
        args: String,
        product: Product?,
        ownerLogin: String?,
    ): CommandResult {
        val owner = ownerLogin ?: return CommandResult(false, "требуется авторизация")
        val id = args.trim().toLongOrNull() ?: return CommandResult(false, "введите id, например: update 5")
        if (!collectionManager.hasId(id)) return CommandResult(false, "элемент с id=$id не найден")
        if (collectionManager.getOwner(id) != owner) return CommandResult(false, "нельзя обновить чужой элемент")
        val p = product ?: ProductReader(io).read()
        return if (collectionManager.updateById(id, p, owner)) {
            CommandResult(true, "элемент обновлён")
        } else {
            CommandResult(false, "не удалось обновить элемент")
        }
    }
}
