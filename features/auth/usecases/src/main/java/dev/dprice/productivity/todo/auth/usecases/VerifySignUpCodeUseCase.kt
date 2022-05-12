package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.VerifyUser
import javax.inject.Inject

// todo: Redundant?
interface VerifySignUpCodeUseCase {
    suspend operator fun invoke(code: String, user: String): VerifyUser
}

class VerifySignUpCodeUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : VerifySignUpCodeUseCase {

    override suspend fun invoke(code: String, user: String): VerifyUser {
        return authenticationSource.verifyNewUser(code, user)
    }
}
