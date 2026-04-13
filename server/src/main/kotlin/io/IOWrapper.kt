package io

class IOWrapper(
    private var currHandler: IOHandler,
) : IOHandler {
    override fun println(message: String) = currHandler.println(message)

    override fun readLine(): String? = currHandler.readLine()

    fun swapHandler(newSwap: IOHandler): IOHandler {
        val old = currHandler
        currHandler = newSwap
        return old
    }
}
