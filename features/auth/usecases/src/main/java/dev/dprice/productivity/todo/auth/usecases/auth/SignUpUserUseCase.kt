package dev.dprice.productivity.todo.auth.usecases.auth

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.SignUp
import javax.inject.Inject

// todo: Redundant?
interface SignUpUserUseCase {

    suspend operator fun invoke(
        username: String,
        email: String,
        password: String
    ): SignUp
}

class SignUpUserUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : SignUpUserUseCase {

    override suspend fun invoke(
        username: String,
        email: String,
        password: String
    ): SignUp {
        return authenticationSource.createUser(username, email, password)
    }
}
