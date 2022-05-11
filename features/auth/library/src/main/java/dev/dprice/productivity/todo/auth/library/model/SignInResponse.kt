package dev.dprice.productivity.todo.auth.library.model

sealed class SignInResponse {
    object Done : SignInResponse()

    data class Code(val username: String) : SignInResponse()

    object AccountDisabled : SignInResponse()

    data class Error(val throwable: Throwable) : SignInResponse()
}
