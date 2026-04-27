package command.commands

import command.Command
import manager.CommandManager
import model.CommandResult

class Help(
    private val commandManager: CommandManager,
) : Command {
    override val name = "help"
    override val description = "show avaliable commands"

    override fun execute(args: String): CommandResult {
        var text = ""
        for (command in commandManager.getCommands().values) {
            text += "${command.name}: ${command.description}\n"
        }
        return CommandResult(true, text.trimEnd())
    }
}
