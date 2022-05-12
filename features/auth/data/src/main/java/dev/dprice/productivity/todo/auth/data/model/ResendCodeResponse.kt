package dev.dprice.productivity.todo.auth.data.model

sealed class ResendCodeResponse {
    object Done : ResendCodeResponse()
    data class Error(val throwable: Throwable) : ResendCodeResponse()
}