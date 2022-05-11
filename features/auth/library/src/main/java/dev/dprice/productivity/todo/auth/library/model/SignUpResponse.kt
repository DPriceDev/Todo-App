package dev.dprice.productivity.todo.auth.library.model

sealed class SignUpResponse {
    object Done : SignUpResponse()

    data class Code(val username: String) : SignUpResponse()

    data class UsernameExists(val username: String) : SignUpResponse()

    data class Error(val throwable: Throwable) : SignUpResponse()
}
