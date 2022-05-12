package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.ForgotPasswordResponse
import javax.inject.Inject

interface SendForgotPasswordUseCase {
    suspend operator fun invoke(email: String) : ForgotPasswordResponse
}

class SendForgotPasswordUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : SendForgotPasswordUseCase {

    override suspend fun invoke(email: String): ForgotPasswordResponse {
        return authenticationSource.sendForgotPassword(email)
    }
}
