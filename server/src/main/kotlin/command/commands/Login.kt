package command.commands

import command.Command
import model.CommandResult
import model.Product

class Login : Command {
    override val name = "login"
    override val description = "authenticate user"

    override fun execute(
        args: String,
        product: Product?,
        ownerLogin: String?,
    ): CommandResult = CommandResult(false, "используйте запрос LOGIN")
}
