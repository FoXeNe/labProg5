package command.commands

import command.Command
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request

class RemoveById(
    private val network: NetworkManager,
) : Command {
    override val name = "remove_by_id"
    override val description = "remove element by id"

    override fun execute(args: String): CommandResult {
        val id = args.trim()
        if (id.isBlank() || id.toLongOrNull() == null) {
            return CommandResult(false, "введите id, к примеру: remove_by_id 5")
        }
        val response =
            network.sendRequest(Request(CommandType.REMOVE_BY_ID, argument = id))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
