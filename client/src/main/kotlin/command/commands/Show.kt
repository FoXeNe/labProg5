package command.commands

import command.Command
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request

class Show(
    private val network: NetworkManager,
) : Command {
    override val name = "show"
    override val description = "show collection elements sorted by price"

    override fun execute(args: String): CommandResult {
        val response =
            network.sendRequest(Request(CommandType.SHOW))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
