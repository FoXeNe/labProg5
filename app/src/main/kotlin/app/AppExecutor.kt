package app

import app.AppInitializer
import command.CommandManager
import io.ConsoleHandler

class AppExecutor {
    fun exec() {
        val io = ConsoleHandler()
        val manager = CommandManager()

        AppInitializer().setup(manager, io)

        while (true) {
            val input = readln()
            manager.initCommand(input, io)
        }
    }
}
