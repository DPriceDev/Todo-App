package dev.dprice.productivity.todo.auth.data.model

sealed class ResendCode {
    object Done : ResendCode()
    object ConnectionError : ResendCode()
    data class Error(val throwable: Throwable) : ResendCode()
}
