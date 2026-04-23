package manager

import model.Request
import model.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class NetworkManager(
    private val host: String,
    private val port: Int,
) {
    private var socket: Socket = ConnectionManager(host, port).connect()
    private var out = DataOutputStream(socket.getOutputStream())
    private var input = DataInputStream(socket.getInputStream())

    fun sendRequest(request: Request): Response? {
        return try {
            send(request)
        } catch (e: IOException) {
            reconnectAndSend(request)
        }
    }

    private fun reconnectAndSend(request: Request): Response? {
        try {
            socket.close()
        } catch (e: Exception) {
        }
        return try {
            socket = ConnectionManager(host, port).connect()
            out = DataOutputStream(socket.getOutputStream())
            input = DataInputStream(socket.getInputStream())
            send(request)
        } catch (e: Exception) {
            null
        }
    }

    private fun send(request: Request): Response {
        val bytes = serialize(request)
        out.writeInt(bytes.size)
        out.write(bytes)
        out.flush()

        val length = input.readInt()
        val responseBytes = ByteArray(length)
        input.readFully(responseBytes)
        return deserialize(responseBytes)
    }

    private fun serialize(request: Request): ByteArray {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(request)
        oos.close()
        return baos.toByteArray()
    }

    private fun deserialize(bytes: ByteArray): Response {
        val bais = ByteArrayInputStream(bytes)
        val ois = ObjectInputStream(bais)
        val obj = ois.readObject() as Response
        ois.close()
        return obj
    }
}
