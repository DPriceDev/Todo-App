package dev.dprice.productivity.todo.auth.data.model

sealed class ForgotPasswordResponse {
    object Done : ForgotPasswordResponse()
    object ConnectionError : ForgotPasswordResponse()
    data class Error(val throwable: Throwable) : ForgotPasswordResponse()
}
