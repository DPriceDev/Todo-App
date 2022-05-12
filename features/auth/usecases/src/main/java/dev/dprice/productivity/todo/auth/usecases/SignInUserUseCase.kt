package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.SignInResponse
import javax.inject.Inject

interface SignInUserUseCase {
    suspend operator fun invoke(
        username: String,
        password: String
    ): SignInResponse
}

class SignInUserUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : SignInUserUseCase {

    override suspend operator fun invoke(
        username: String,
        password: String
    ): SignInResponse {
        return authenticationSource.signInUser(username, password)
    }
}
