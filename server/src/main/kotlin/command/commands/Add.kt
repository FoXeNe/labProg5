package command.commands

import command.Command
import io.IOHandler
import manager.CollectionManager
import model.CommandResult
import model.Product
import reader.ProductReader

class Add(
    private val io: IOHandler,
    private val collectionManager: CollectionManager,
) : Command {
    override val name = "add"
    override val description = "add product"

    override fun execute(
        args: String,
        product: Product?,
        ownerLogin: String?,
    ): CommandResult {
        val owner = ownerLogin ?: return CommandResult(false, "требуется авторизация")
        val p = product ?: ProductReader(io).read()
        collectionManager.addProduct(p, owner)
        return CommandResult(true, "продукт добавлен")
    }
}
