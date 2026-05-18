package command.commands

import command.Command
import model.CommandResult
import model.Product

class Register : Command {
    override val name = "register"
    override val description = "register a new user"

    override fun execute(
        args: String,
        product: Product?,
        ownerLogin: String?,
    ): CommandResult = CommandResult(false, "используйте запрос REGISTER")
}
