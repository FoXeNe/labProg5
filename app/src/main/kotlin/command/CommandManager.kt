package command

import command.commands.*
import io.IOHandler

class CommandManager {
    fun initCommand(command: String, io: IOHandler) {
        when (command) {
            "add" -> Add(io).execute()
            "exit" -> Exit(io).execute()
        }
    }
}
