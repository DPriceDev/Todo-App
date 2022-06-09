package dev.dprice.productivity.todo.auth.usecases.auth

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.SignIn
import javax.inject.Inject

interface SignInUserUseCase {
    suspend operator fun invoke(
        username: String,
        password: String
    ): SignIn
}

class SignInUserUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : SignInUserUseCase {

    override suspend operator fun invoke(
        username: String,
        password: String
    ): SignIn {
        return authenticationSource.signInUser(username, password)
    }
}
