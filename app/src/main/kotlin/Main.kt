import command.CommandManager
import command.commands.*
import io.ConsoleHandler

fun main() {
    val io = ConsoleHandler()
    val manager = CommandManager()

    while (true) {
        val input = readln()
        manager.initCommand(input, io)
    }
}
