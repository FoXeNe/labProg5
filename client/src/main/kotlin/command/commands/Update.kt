package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request
import reader.ProductReader

class Update(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "update"
    override val description = "update element by id"

    override fun execute(args: String): CommandResult {
        val id = args.trim()
        if (id.isBlank() || id.toLongOrNull() == null) {
            return CommandResult(false, "введите id, к примеру: update 5")
        }
        val product = ProductReader(io).read()
        val response =
            network.sendRequest(Request(CommandType.UPDATE, argument = id, product = product))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
