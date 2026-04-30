package manager

import model.Request
import model.Response
import java.io.File
import java.io.RandomAccessFile
import java.util.LinkedList
import java.util.logging.Logger

class SharedFileSync(
    private val filePath: String,
    private val collectionManager: CollectionManager,
    private val commandManager: CommandManager,
) {
    private val logger = Logger.getLogger(SharedFileSync::class.java.name)
    private val jsonManager = JsonManager(filePath)

    fun handle(request: Request): Response {
        val file = File(filePath)
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            file.createNewFile()
            jsonManager.writeCollection(LinkedList())
        }
        val raf = RandomAccessFile(filePath, "rw")
        val lock = raf.channel.lock()
        try {
            reloadCollection()
            val response = executeCommand(request)
            if (collectionManager.wasModified()) {
                jsonManager.writeCollection(collectionManager.getCollection())
                collectionManager.resetModified()
            }
            return response
        } finally {
            lock.release()
            raf.close()
        }
    }

    private fun reloadCollection() {
        try {
            val file = File(filePath)
            if (file.exists() && file.length() > 0) {
                collectionManager.replaceCollection(jsonManager.readCollection())
            }
        } catch (e: Exception) {
            logger.warning("не удалось перезагрузить коллекцию: ${e.message}")
        }
    }

    private fun executeCommand(request: Request): Response {
        val commandName = request.commandType.name.lowercase()
        val command = commandManager.getCommands()[commandName]
            ?: return Response(false, "неизвестная команда: $commandName")
        val args = request.argument ?: ""
        val result = command.execute(args, request.product)
        return Response(result.success, result.message, result.collection)
    }
}
