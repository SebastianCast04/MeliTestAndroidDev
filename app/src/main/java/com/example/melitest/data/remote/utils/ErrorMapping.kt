package com.example.melitest.data.remote.utils

fun Throwable.toNetworkError(): NetworkError = when (this) {
    is java.net.SocketTimeoutException -> NetworkError.Timeout
    is java.io.IOException -> NetworkError.NoInternet
    is retrofit2.HttpException -> {
        val code = code()
        val body = response()?.errorBody()?.string()
        NetworkError.Http(code, body)
    }
    is NetworkError -> this
    else -> NetworkError.Unknown(this)
}

fun NetworkError.toUserMessage(): String = when (this) {
    NetworkError.NoInternet -> "Estás sin conexión. Revisa tu Internet e inténtalo de nuevo."
    NetworkError.Timeout -> "La solicitud tardó demasiado. Intenta nuevamente."
    is NetworkError.Http -> when (code) {
        in 500..599 -> "Nuestros servidores están con problemas. Intenta más tarde."
        404 -> "No encontramos lo que buscabas."
        401, 403 -> "No tienes permisos para esta acción."
        else -> "No pudimos completar tu solicitud (código $code)."
    }
    is NetworkError.Unknown -> "Ups, algo salió mal. Intenta nuevamente."
}
