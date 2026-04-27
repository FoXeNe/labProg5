package app

import command.commands.*
import io.IOWrapper
import manager.CommandManager
import manager.NetworkManager

class AppInitializer {
    fun setup(
        commandManager: CommandManager,
        io: IOWrapper,
        app: AppExecutor,
        network: NetworkManager,
    ) {
        commandManager.register(Help(commandManager))
        commandManager.register(History(commandManager))
        commandManager.register(Exit { app.stop() })
        commandManager.register(ExecuteScript(io, commandManager))

        commandManager.register(Add(io, network))
        commandManager.register(AddIfMin(io, network))
        commandManager.register(Clear(network))
        commandManager.register(FilterByManufacturer(network))
        commandManager.register(FilterGreaterThanManufacturer(network))
        commandManager.register(Info(network))
        commandManager.register(RemoveById(network))
        commandManager.register(RemoveFirst(network))
        commandManager.register(Show(network))
        commandManager.register(SumOfPrice(network))
        commandManager.register(Update(io, network))
    }
}
