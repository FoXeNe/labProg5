package io

interface IOHandler {
    fun println(message: String)

    fun readLine(): String?

    fun readLong(string: String): Long {
        while (true) {
            println(string)
            val input = readLine()?.toLongOrNull()
            if (input != null) return input
            println("ожидался long")
        }
    }

    fun readFloat(string: String): Float {
        while (true) {
            println(string)
            val input = readLine()?.toFloatOrNull()
            if (input != null) return input
            println("ожидался float")
        }
    }

    fun readString(string: String): String {
        while (true) {
            println(string)
            val input = readLine()
            if (!input.isNullOrBlank()) return input
            println("ожидался string")
        }
    }
}
