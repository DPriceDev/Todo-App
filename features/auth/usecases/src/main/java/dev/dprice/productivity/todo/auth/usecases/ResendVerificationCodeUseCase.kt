package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.library.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.library.model.ResendCodeResponse
import javax.inject.Inject

interface ResendVerificationCodeUseCase {
    suspend operator fun invoke(username: String) : ResendCodeResponse
}

class ResendVerificationCodeUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : ResendVerificationCodeUseCase {

    override suspend operator fun invoke(username: String) : ResendCodeResponse {
        return authenticationSource.resendVerificationCode(username)
    }
}
