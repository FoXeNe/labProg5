package app

import command.commands.*
import io.IOWrapper
import manager.CollectionManager
import manager.CommandManager
import manager.JsonManager
import java.util.LinkedList

const val ENV_FILE = "COLLECTION_FILE"

class AppInitializer {
    fun setup(
        commandManager: CommandManager,
        io: IOWrapper,
        app: AppExecutor,
    ) {
        val filePath = System.getenv(ENV_FILE)

        val collection =
            if (filePath != null) {
                io.println("коллекция загружена")
                JsonManager(filePath).readCollection()
            } else {
                io.println("коллекция не загружена")
                LinkedList()
            }

        val collectionManager = CollectionManager(io, collection)

        commandManager.register(Add(io, collectionManager))
        commandManager.register(Clear(io, collectionManager))
        commandManager.register(Help(io, commandManager))
        commandManager.register(History(io, commandManager))
        commandManager.register(Info(io, collectionManager))
        commandManager.register(RemoveById(io, collectionManager))
        commandManager.register(RemoveFirst(io, collectionManager))
        commandManager.register(Update(io, collectionManager))
        commandManager.register(Exit(io, { app.stop() }))
        commandManager.register(ExecuteScript(io, commandManager))
        commandManager.register(Show(io, collectionManager))
        commandManager.register(Save(io, collectionManager, filePath))
        commandManager.register(SumOfPrice(io, collectionManager))
        commandManager.register(FilterByManufacturer(io, collectionManager))
        commandManager.register(FilterGreaterThanManufacturer(io, collectionManager))
    }
}
