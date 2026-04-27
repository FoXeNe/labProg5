package command.commands

import command.Command
import model.CommandResult

class Exit(
    private val stop: () -> Unit,
) : Command {
    override val name = "exit"
    override val description = "stop app execution"

    override fun execute(args: String): CommandResult {
        stop()
        return CommandResult(true, "завершение процесса")
    }
}
