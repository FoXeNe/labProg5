import command.CommandManager
import command.commands.*
import io.ConsoleHandler
import app.AppInitializer

fun main() {
    val io = ConsoleHandler()
    val manager = CommandManager()

    AppInitializer().setup(manager, io)

    while (true) {
        val input = readln()
        manager.initCommand(input, io)
    }
}
