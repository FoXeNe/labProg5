package command

import io.IOHandler

class CommandManager {
    private val commands = mutableMapOf<String, Command>()

    fun register(command: Command) {
        commands[command.name] = command
    }

    fun initCommand(
        input: String,
        io: IOHandler,
    ) {
        val resInput = input.trim().split(" ")
        val name = resInput[0]
        var args: String
        if (resInput.size > 1) {
            args = resInput[1]
        } else {
            args = " "
        }
        val command = commands[name]

        if (command != null) {
            command.execute(args)
        } else {
            io.println("команда не найдена")
        }
    }

    fun getCommands() = commands
}
