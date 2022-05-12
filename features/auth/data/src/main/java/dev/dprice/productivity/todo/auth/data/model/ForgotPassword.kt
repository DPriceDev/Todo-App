package dev.dprice.productivity.todo.auth.data.model

sealed class ForgotPassword {
    object Done : ForgotPassword()
    object ConnectionError : ForgotPassword()
    data class Error(val throwable: Throwable) : ForgotPassword()
}
