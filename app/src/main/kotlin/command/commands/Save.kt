package command.commands

import command.Command
import io.IOHandler
import manager.CollectionManager
import manager.JsonManager

class Save(
    private val io: IOHandler,
    private val collectionManager: CollectionManager,
    private val filePath: String?,
) : Command {
    override val name = "save"
    override val description = "save collection to the file"

    override fun execute(args: String) {
        if (filePath == null) {
            io.println("путь к файлу не задан")
            return
        }
        JsonManager(filePath).writeCollection(collectionManager.getCollection())
        io.println("коллекция сохранена")
    }
}
