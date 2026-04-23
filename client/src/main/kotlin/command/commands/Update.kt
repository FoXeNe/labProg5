package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandType
import model.Request
import reader.ProductReader

class Update(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "update"
    override val description = "update element by id"

    override fun execute(args: String) {
        val id = args.trim()
        if (id.isBlank() || id.toLongOrNull() == null) {
            io.println("введите id, к примеру: update 5")
            return
        }
        val product = ProductReader(io).read()
        val response = network.sendRequest(Request(CommandType.UPDATE, argument = id, product = product))
        if (response == null) {
            io.println("сервер недоступен, попробуйте позже")
        } else {
            io.println(response.message)
        }
    }
}
