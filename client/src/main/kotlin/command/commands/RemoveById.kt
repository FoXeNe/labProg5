package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandType
import model.Request

class RemoveById(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "remove_by_id"
    override val description = "remove element by id"

    override fun execute(args: String) {
        val id = args.trim()
        if (id.isBlank() || id.toLongOrNull() == null) {
            io.println("введите id, к примеру: remove_by_id 5")
            return
        }
        val response = network.sendRequest(Request(CommandType.REMOVE_BY_ID, argument = id))
        if (response == null) {
            io.println("сервер недоступен, попробуйте позже")
        } else {
            io.println(response.message)
        }
    }
}
