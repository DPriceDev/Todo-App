package dev.dprice.productivity.todo.auth.data.model

sealed class SignIn {
    object Done : SignIn()

    data class Code(val username: String) : SignIn()

    object AccountDisabled : SignIn()

    object NetworkError : SignIn()

    data class Error(val throwable: Throwable) : SignIn()
}
