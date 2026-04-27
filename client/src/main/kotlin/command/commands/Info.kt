package command.commands

import command.Command
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request

class Info(
    private val network: NetworkManager,
) : Command {
    override val name = "info"
    override val description = "show collection info"

    override fun execute(args: String): CommandResult {
        val response =
            network.sendRequest(Request(CommandType.INFO))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
