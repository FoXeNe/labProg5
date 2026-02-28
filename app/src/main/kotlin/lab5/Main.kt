package lab5

import lab5.command.CommandManager

fun main() {
    val commandManager = CommandManager()

    while (true) {
        val input = readlnOrNull().toString()

        commandManager.initCommand(input)
    }
}
