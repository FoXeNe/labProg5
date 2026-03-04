package command.commands

import command.Command
import io.IOHandler
import manager.CollectionManager

class Info(
    private val io: IOHandler,
    private val collectionManager: CollectionManager,
) : Command {
    override val name = "info"
    override val description = "show collection info"

    override fun execute(args: String) {
        io.println(collectionManager.getInfoString())
    }
}
