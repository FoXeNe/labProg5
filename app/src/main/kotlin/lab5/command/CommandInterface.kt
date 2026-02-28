package lab5.command

interface CommandInterface {
    val name: String
    val description: String

    fun execute(args: String? = null)
}
