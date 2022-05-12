package dev.dprice.productivity.todo.auth.data.model

sealed class ForgotPasswordThirteen {
    object Done : ForgotPasswordThirteen()
    object ConnectionError : ForgotPasswordThirteen()
    data class Error(val throwable: Throwable) : ForgotPasswordThirteen()
}
