package command.commands

import command.Command
import command.ProductReader
import io.IOHandler
import manager.CollectionManager

class Add(
    private val io: IOHandler,
    private val manager: CollectionManager,
) : Command {
    override val name = "add"
    override val description = "add product"

    override fun execute(args: String?) {
        io.println("добавляем продукт")
        manager.addProduct(ProductReader(io).read())
    }
}
