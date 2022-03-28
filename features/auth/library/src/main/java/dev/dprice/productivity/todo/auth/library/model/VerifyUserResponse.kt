package dev.dprice.productivity.todo.auth.library.model

sealed class VerifyUserResponse {
    object Done : VerifyUserResponse()
    data class Error(val throwable: Throwable) : VerifyUserResponse()
}
