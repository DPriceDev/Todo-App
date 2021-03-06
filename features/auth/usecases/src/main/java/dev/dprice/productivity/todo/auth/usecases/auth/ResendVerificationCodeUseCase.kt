package dev.dprice.productivity.todo.auth.usecases.auth

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.ResendCode
import javax.inject.Inject

interface ResendVerificationCodeUseCase {
    suspend operator fun invoke(username: String): ResendCode
}

class ResendVerificationCodeUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : ResendVerificationCodeUseCase {

    override suspend operator fun invoke(username: String) : ResendCode {
        return authenticationSource.resendVerificationCode(username)
    }
}
