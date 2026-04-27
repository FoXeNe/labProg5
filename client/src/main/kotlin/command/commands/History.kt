package command.commands

import command.Command
import manager.CommandManager
import model.CommandResult

class History(
    private val commandManager: CommandManager,
) : Command {
    override val name = "history"
    override val description = "last 10 command was written"

    override fun execute(args: String): CommandResult {
        val history = commandManager.getHistory()
        if (history.isEmpty()) {
            return CommandResult(true, "история пуста")
        }
        var text = ""
        for (command in history) {
            text += command.name + "\n"
        }
        return CommandResult(true, text.trimEnd())
    }
}
