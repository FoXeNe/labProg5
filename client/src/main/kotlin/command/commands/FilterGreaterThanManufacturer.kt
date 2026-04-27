package command.commands

import command.Command
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request

class FilterGreaterThanManufacturer(
    private val network: NetworkManager,
) : Command {
    override val name = "filter_greater_than_manufacturer"
    override val description = "return the elements whose manufacturer field value is greater than value"

    override fun execute(args: String): CommandResult {
        val name = args.trim()
        if (name.isBlank()) {
            return CommandResult(false, "укажите имя производителя, например: filter_greater_than_manufacturer NAME")
        }
        val response =
            network.sendRequest(Request(CommandType.FILTER_GREATER_THAN_MANUFACTURER, argument = name))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
