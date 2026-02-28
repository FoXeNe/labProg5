package app

import command.commands.*
import command.CommandManager
import io.IOHandler

class AppInitializer {
    fun setup(
        manager: CommandManager,
        io: IOHandler,
    ) {
        manager.register(Add(io))
        manager.register(Exit(io))
    }
}
