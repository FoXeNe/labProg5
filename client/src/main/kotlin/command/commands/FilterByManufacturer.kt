package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandType
import model.Request

class FilterByManufacturer(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "filter_by_manufacturer"
    override val description = "display the elements whose manufacturer field value matches value"

    override fun execute(args: String) {
        val name = args.trim()
        if (name.isBlank()) {
            io.println("укажите имя производителя, например: filter_by_manufacturer NAME")
            return
        }
        val response = network.sendRequest(Request(CommandType.FILTER_BY_MANUFACTURER, argument = name))
        if (response == null) {
            io.println("сервер недоступен, попробуйте позже")
            return
        }
        io.println(response.message)
        response.collection?.forEach { io.println(it.toString()) }
    }
}
