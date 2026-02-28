package io

interface IOHandler {
    fun println(message: String)
    fun readLine(): String?
}
