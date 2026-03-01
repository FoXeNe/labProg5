package app

import app.AppInitializer
import command.CommandManager
import io.ConsoleHandler

class AppExecutor {
    var interactiveMode = true

    fun exec() {
        val io = ConsoleHandler()
        val manager = CommandManager()

        AppInitializer().setup(manager, io, this)

        while (interactiveMode) {
            val input = readln()
            manager.initCommand(input, io)
        }
    }

    fun stop() {
        interactiveMode = false
    }
}
