package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.library.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.library.model.SignUpResponse
import javax.inject.Inject

// todo: Redundant?
interface SignUpUserUseCase {

    suspend operator fun invoke(
        username: String,
        email: String,
        password: String
    ): SignUpResponse
}

class SignUpUserUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : SignUpUserUseCase {

    override suspend fun invoke(
        username: String,
        email: String,
        password: String
    ): SignUpResponse {
        return authenticationSource.createUser(username, email, password)
    }
}
