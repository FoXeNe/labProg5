package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandType
import model.Request
import reader.ProductReader

class Add(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "add"
    override val description = "add product"

    override fun execute(args: String) {
        val product = ProductReader(io).read()
        val response = network.sendRequest(Request(CommandType.ADD, product = product))
        if (response == null) {
            io.println("сервер недоступен, попробуйте позже")
        } else {
            io.println(response.message)
        }
    }
}
