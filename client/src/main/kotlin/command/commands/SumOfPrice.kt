package command.commands

import command.Command
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request

class SumOfPrice(
    private val network: NetworkManager,
) : Command {
    override val name = "sum_of_price"
    override val description = "get the sum of the values in the price field for all items"

    override fun execute(args: String): CommandResult {
        val response =
            network.sendRequest(Request(CommandType.SUM_OF_PRICE))
                ?: return CommandResult(false, "сервер недоступен")
        return CommandResult(response.success, response.message, response.collection)
    }
}
