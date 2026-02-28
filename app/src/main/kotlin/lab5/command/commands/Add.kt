package lab5.command.commands

import lab5.command.CommandInterface

class Add : CommandInterface {
    override val name = "add"
    override val description = "add product"

    override fun execute(args: String?) {
        println("fdada")
    }
}
