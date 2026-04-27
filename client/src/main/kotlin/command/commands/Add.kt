package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request
import reader.ProductReader

class Add(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "add"
    override val description = "add product"

    override fun execute(args: String): CommandResult {
        val product = ProductReader(io).read()
        val response =
            network.sendRequest(Request(CommandType.ADD, product = product))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
