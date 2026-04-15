package app

import io.ConsoleHandler
import io.IOWrapper
import manager.CommandManager
import manager.ConnectionManager
import java.io.BufferedReader
import java.io.InputStreamReader

class AppExecutor {
    var interactiveMode = true

    fun exec() {
        val io = IOWrapper(ConsoleHandler())
        val manager = CommandManager()
        val connManager = ConnectionManager("localhost", 9090)

        AppInitializer().setup(manager, io, this)

        io.println("сервер запущен, введите help для получения информации о командах")

        val consoleReader = BufferedReader(InputStreamReader(System.`in`))

        while (interactiveMode) {
            connManager.exec()
            if (consoleReader.ready()) {
                val input = consoleReader.readLine()
                if (!input.isNullOrBlank()) {
                    manager.initCommand(input, io)
                }
            }
        }
    }

    fun stop() {
        interactiveMode = false
    }
}
