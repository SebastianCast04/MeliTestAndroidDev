package com.example.melitest.data.remote.utils

sealed class NetworkError(message: String? = null, cause: Throwable? = null) : Throwable(message, cause) {
    object NoInternet : NetworkError("Sin conexión")
    object Timeout : NetworkError("Tiempo de espera agotado")
    data class Http(val code: Int, val body: String?) : NetworkError("HTTP $code")
    data class Unknown(val origin: Throwable) : NetworkError(origin.message, origin)
}
