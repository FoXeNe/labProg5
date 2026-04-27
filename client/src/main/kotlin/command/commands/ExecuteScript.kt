package command.commands

import command.Command
import io.IOWrapper
import io.ScriptHandler
import manager.CommandManager
import model.CommandResult
import java.io.File
import java.io.FileNotFoundException

class ExecuteScript(
    private val io: IOWrapper,
    private val commandManager: CommandManager,
    private val executingFile: MutableSet<String> = mutableSetOf(),
) : Command {
    override val name = "execute_script"
    override val description = "read and execute script"

    override fun execute(args: String): CommandResult {
        val fileName = args.trim()
        if (fileName.isBlank()) {
            return CommandResult(false, "укажите имя файла, к примеру: execute_script script.txt")
        }

        val canonicalPath =
            try {
                File(fileName).canonicalPath
            } catch (e: Exception) {
                fileName
            }

        if (canonicalPath in executingFile) {
            return CommandResult(false, "рекурсия, не выполняйте файл в нем же")
        }

        val scriptHandler =
            try {
                ScriptHandler(fileName)
            } catch (e: FileNotFoundException) {
                return CommandResult(false, "не удалось открыть файл")
            }

        executingFile.add(canonicalPath)
        val previousSwap = io.swapHandler(scriptHandler)
        try {
            var line = io.readLine()
            while (line != null) {
                if (line.isNotBlank()) {
                    io.println("> $line")
                    val result = commandManager.initCommand(line)
                    io.println(result.message)
                    result.collection?.forEach { io.println(it.toString()) }
                }
                line = io.readLine()
            }
        } finally {
            io.swapHandler(previousSwap)
            scriptHandler.close()
            executingFile.remove(canonicalPath)
        }

        return CommandResult(true, "скрипт выполнен")
    }
}
