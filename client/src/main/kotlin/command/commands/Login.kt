package command.commands

import command.Command
import io.IOHandler
import manager.NetworkManager
import model.CommandResult
import model.CommandType
import model.Request

class Login(
    private val io: IOHandler,
    private val network: NetworkManager,
) : Command {
    override val name = "login"
    override val description = "login with existing account"

    override fun execute(args: String): CommandResult {
        val login = io.readString("введите логин")
        val password = io.readString("введите пароль")
        val response =
            network.sendRaw(Request(CommandType.LOGIN, login = login, password = password))
                ?: return CommandResult(false, "сервер недоступен")
        if (response.success) {
            network.setCredentials(login, password)
        }
        return CommandResult(response.success, response.message)
    }
}
