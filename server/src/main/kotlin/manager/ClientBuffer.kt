package manager

import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class ClientBuffer {
    private val byteOut = ByteArrayOutputStream()

    fun append(
        bytes: ByteArray,
        length: Int,
    ) {
        byteOut.write(bytes, 0, length)
    }

    fun readMessage(): ByteArray? {
        val data = byteOut.toByteArray()
        if (data.size < 4) return null

        val msgLength = ByteBuffer.wrap(data).int
        if (data.size < 4 + msgLength) return null

        val msg = data.copyOfRange(4, 4 + msgLength)
        byteOut.reset()
        if (data.size > 4 + msgLength) {
            byteOut.write(data, 4 + msgLength, data.size - 4 - msgLength)
        }
        return msg
    }
}
