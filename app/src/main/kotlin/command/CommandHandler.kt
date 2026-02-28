package command

interface CommandHandler {
    val name: String
    val description: String
    fun execute(args: String? = null)
}
