package command.commands

import command.Command
import io.IOHandler
import kotlin.system.exitProcess

class Exit(
    private val io: IOHandler,
) : Command {
    override val name = "exit"
    override val description = "exit"

    override fun execute(args: String?) {
        io.println("завершение процесса")
        exitProcess(0)
    }
}
