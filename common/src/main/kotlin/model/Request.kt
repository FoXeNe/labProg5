package model

import java.io.Serializable

data class Request(
    val commandType: CommandType,
    val argument: String? = null,
    val product: Product? = null,
    val login: String? = null,
    val password: String? = null,
) : Serializable {
    companion object {
        private const val serialVersionUID = 2L
    }
}
