package io

class ConsoleHandler : IOHandler{
    override fun println(message: String) {
        kotlin.io.println(message)
    }

    override fun readLine(): String? {
        return readlnOrNull()
    }
}
