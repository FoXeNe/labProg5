package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandType
import model.Request

class Show(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "show"
    override val description = "show collection elements sorted by price"

    override fun execute(args: String) {
        val response = network.sendRequest(Request(CommandType.SHOW))
        if (response == null) {
            io.println("сервер недоступен, попробуйте позже")
            return
        }
        io.println(response.message)
        response.collection?.forEach { io.println(it.toString()) }
    }
}
