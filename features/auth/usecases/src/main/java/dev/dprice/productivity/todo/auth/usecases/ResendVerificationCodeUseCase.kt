package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.ResendCode

interface ResendVerificationCodeUseCase {
    suspend operator fun invoke(username: String): ResendCode
}

class ResendVerificationCodeUseCaseImpl(
    private val authenticationSource: AuthenticationSource
) : ResendVerificationCodeUseCase {

    override suspend operator fun invoke(username: String) : ResendCode {
        return authenticationSource.resendVerificationCode(username)
    }
}
