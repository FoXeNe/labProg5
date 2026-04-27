package command.commands

import command.Command
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request

class Clear(
    private val network: NetworkManager,
) : Command {
    override val name = "clear"
    override val description = "clear collection"

    override fun execute(args: String): CommandResult {
        val response =
            network.sendRequest(Request(CommandType.CLEAR))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
