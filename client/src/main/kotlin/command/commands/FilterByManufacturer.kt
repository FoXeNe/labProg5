package command.commands

import command.Command
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request

class FilterByManufacturer(
    private val network: NetworkManager,
) : Command {
    override val name = "filter_by_manufacturer"
    override val description = "display the elements whose manufacturer field value matches value"

    override fun execute(args: String): CommandResult {
        val name = args.trim()
        if (name.isBlank()) {
            return CommandResult(false, "укажите имя производителя, например: filter_by_manufacturer NAME")
        }
        val response =
            network.sendRequest(Request(CommandType.FILTER_BY_MANUFACTURER, argument = name))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
