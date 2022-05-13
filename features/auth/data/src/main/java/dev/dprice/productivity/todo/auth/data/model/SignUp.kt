package dev.dprice.productivity.todo.auth.data.model

sealed class SignUp {
    object Done : SignUp()

    data class Code(val username: String) : SignUp()

    data class UsernameExists(val username: String) : SignUp()

    data class Error(val throwable: Throwable) : SignUp()

    object ConnectionError : SignUp()
}
