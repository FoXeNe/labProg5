package command.commands

import command.Command
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request

class RemoveFirst(
    private val network: NetworkManager,
) : Command {
    override val name = "remove_first"
    override val description = "remove first element"

    override fun execute(args: String): CommandResult {
        val response =
            network.sendRequest(Request(CommandType.REMOVE_FIRST))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
