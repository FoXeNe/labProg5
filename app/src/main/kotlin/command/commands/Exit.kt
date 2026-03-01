package command.commands

import command.CommandHandler
import io.IOHandler
import kotlin.system.exitProcess

class Exit(
    private val io: IOHandler,
) : CommandHandler {
    override val name = "exit"
    override val description = "exit"

    override fun execute(args: String?) {
        io.println("завершение процесса")
        exitProcess(0)
    }
}
