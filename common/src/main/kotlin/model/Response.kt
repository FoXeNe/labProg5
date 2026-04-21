package model

import java.io.Serializable

data class Response(
    val success: Boolean,
    val message: String,
    val collection: List<Product>? = null,
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
