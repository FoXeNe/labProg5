package lab5.command

import lab5.command.commands.Add

class CommandManager {
    fun initCommand(command: String) {
        when (command) {
            "add" -> Add().execute()
        }
    }
}
