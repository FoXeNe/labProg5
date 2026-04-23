package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandType
import model.Request

class RemoveFirst(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "remove_first"
    override val description = "remove first element"

    override fun execute(args: String) {
        val response = network.sendRequest(Request(CommandType.REMOVE_FIRST))
        if (response == null) {
            io.println("сервер недоступен, попробуйте позже")
        } else {
            io.println(response.message)
        }
    }
}
