package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandType
import model.Request

class SumOfPrice(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "sum_of_price"
    override val description = "get the sum of the values in the price field for all items"

    override fun execute(args: String) {
        val response = network.sendRequest(Request(CommandType.SUM_OF_PRICE))
        if (response == null) {
            io.println("сервер недоступен, попробуйте позже")
        } else {
            io.println(response.message)
        }
    }
}
