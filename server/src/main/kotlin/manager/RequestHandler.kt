package manager

import model.Request
import model.Response

class RequestHandler(
    private val commandManager: CommandManager,
    private val sharedFileSync: SharedFileSync? = null,
) {
    fun handle(request: Request): Response =
        try {
            if (sharedFileSync != null) {
                sharedFileSync.handle(request)
            } else {
                val commandName = request.commandType.name.lowercase()
                val command = commandManager.getCommands()[commandName]
                    ?: return Response(false, "неизвестная команда: $commandName")
                val args = request.argument ?: ""
                val result = command.execute(args, request.product)
                Response(result.success, result.message, result.collection)
            }
        } catch (e: Exception) {
            Response(false, "ошибка: ${e.message}")
        }
}
