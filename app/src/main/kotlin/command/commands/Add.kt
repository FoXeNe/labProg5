package command.commands

import command.Command
import command.ProductReader
import io.IOHandler

class Add(
    private val io: IOHandler,
) : Command {
    override val name = "add"
    override val description = "add product"

    override fun execute(args: String?) {
        io.println("добавляем продукт")
        ProductReader().read()
    }
}
