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
        commandManager.register(Help(io, commandManager))
        commandManager.register(History(io, commandManager))
        commandManager.register(Exit(io, { app.stop() }))
        commandManager.register(ExecuteScript(io, commandManager))

        commandManager.register(Add(io, network))
        commandManager.register(AddIfMin(io, network))
        commandManager.register(Clear(io, network))
        commandManager.register(FilterByManufacturer(io, network))
        commandManager.register(FilterGreaterThanManufacturer(io, network))
        commandManager.register(Info(io, network))
        commandManager.register(RemoveById(io, network))
        commandManager.register(RemoveFirst(io, network))
        commandManager.register(Show(io, network))
        commandManager.register(SumOfPrice(io, network))
        commandManager.register(Update(io, network))
    }
}
