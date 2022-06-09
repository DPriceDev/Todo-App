package dev.dprice.productivity.todo.auth.data.model

sealed class VerifyUser {
    object Done : VerifyUser()
    object ExpiredCode : VerifyUser()
    object IncorrectCode : VerifyUser()
    object ConnectionError : VerifyUser()
    data class Error(val throwable: Throwable) : VerifyUser()
}
