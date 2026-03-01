package app

import command.commands.*
import command.CommandManager
import manager.CollectionManager
import io.IOHandler

class AppInitializer {
    fun setup(
        manager: CommandManager,
        io: IOHandler,
    ) {
        val collectionManager = CollectionManager(io)

        manager.register(Add(io, collectionManager))
        manager.register(Exit(io))
    }
}
