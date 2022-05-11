package dev.dprice.productivity.todo.auth.library.model

sealed class ResendCodeResponse {
    object Done : ResendCodeResponse()
    data class Error(val throwable: Throwable) : ResendCodeResponse()
}