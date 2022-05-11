package dev.dprice.productivity.todo.auth.library.model

sealed class VerifyUserResponse {
    object Done : VerifyUserResponse()
    object ExpiredCode : VerifyUserResponse()
    object IncorrectCode : VerifyUserResponse()
    object ConnectionError : VerifyUserResponse()
    data class Error(val throwable: Throwable) : VerifyUserResponse()
}
