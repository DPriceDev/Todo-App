package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.VerifyUserResponse
import javax.inject.Inject

// todo: Redundant?
interface VerifySignUpCodeUseCase {
    suspend operator fun invoke(code: String, user: String): VerifyUserResponse
}

class VerifySignUpCodeUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : VerifySignUpCodeUseCase {

    override suspend fun invoke(code: String, user: String): VerifyUserResponse {
        return authenticationSource.verifyNewUser(code, user)
    }
}
