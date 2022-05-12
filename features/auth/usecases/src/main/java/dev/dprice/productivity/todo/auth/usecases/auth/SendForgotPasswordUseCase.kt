package dev.dprice.productivity.todo.auth.usecases.auth

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.ForgotPasswordThirteen
import javax.inject.Inject

interface SendForgotPasswordUseCase {
    suspend operator fun invoke(email: String): ForgotPasswordThirteen
}

class SendForgotPasswordUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : SendForgotPasswordUseCase {

    override suspend fun invoke(email: String): ForgotPasswordThirteen {
        return authenticationSource.sendForgotPassword(email)
    }
}
