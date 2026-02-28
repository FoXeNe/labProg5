package command.commands

import command.CommandHandler
import io.IOHandler

class Add(private val io: IOHandler) : CommandHandler {
    override val name = "add"
    override val description = "add product"

    override fun execute(args: String?) {
        io.println("fdada")
    }
}
