package dev.dprice.productivity.todo.auth.data.model

sealed class ResetPassword {
    object Done : ResetPassword()
    object ConnectionError : ResetPassword()
    data class Error(val throwable: Throwable) : ResetPassword()
}
