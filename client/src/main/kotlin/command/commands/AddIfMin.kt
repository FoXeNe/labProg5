package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request
import reader.ProductReader

class AddIfMin(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "add_if_min"
    override val description = "add an object if its value is less than the minimum value in the collection"

    override fun execute(args: String): CommandResult {
        val product = ProductReader(io).read()
        val response =
            network.sendRequest(Request(CommandType.ADD_IF_MIN, product = product))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
