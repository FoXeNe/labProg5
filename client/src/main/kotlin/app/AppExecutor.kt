package app

import io.ConsoleHandler
import io.IOWrapper
import manager.CommandManager
import manager.NetworkManager

class AppExecutor {
    var interactiveMode = true

    fun exec() {
        val io = IOWrapper(ConsoleHandler())
        val manager = CommandManager()
        val network = NetworkManager("localhost", 9090)

        AppInitializer().setup(manager, io, this, network)

        io.println("введите help для получения информации о командах")

        while (interactiveMode) {
            val input = io.readLine() ?: break
            if (input.isNotBlank()) {
                val result = manager.initCommand(input)
                io.println(result.message)
                result.collection?.forEach { io.println(it.toString()) }
            }
        }
    }

    fun stop() {
        interactiveMode = false
    }
}
