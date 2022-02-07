package dev.dprice.productivity.todo.auth.model

sealed class SignUpResponse {
    object Done : SignUpResponse()

    data class Code(
        val username: String
    ) : SignUpResponse()

    data class UserExists(
        val username: String
    ) : SignUpResponse()

    data class Error(val throwable: Throwable) : SignUpResponse()
}
