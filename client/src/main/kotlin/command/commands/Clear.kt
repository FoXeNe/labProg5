package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandType
import model.Request

class Clear(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "clear"
    override val description = "clear collection"

    override fun execute(args: String) {
        val response = network.sendRequest(Request(CommandType.CLEAR))
        if (response == null) {
            io.println("сервер недоступен, попробуйте позже")
        } else {
            io.println(response.message)
        }
    }
}
