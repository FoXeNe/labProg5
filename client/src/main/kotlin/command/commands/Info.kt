package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandType
import model.Request

class Info(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "info"
    override val description = "show collection info"

    override fun execute(args: String) {
        val response = network.sendRequest(Request(CommandType.INFO))
        if (response == null) {
            io.println("сервер недоступен, попробуйте позже")
        } else {
            io.println(response.message)
        }
    }
}
