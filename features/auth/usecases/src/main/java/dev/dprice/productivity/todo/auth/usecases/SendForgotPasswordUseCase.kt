package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.ForgotPassword

interface SendForgotPasswordUseCase {
    suspend operator fun invoke(username: String): ForgotPassword
}

class SendForgotPasswordUseCaseImpl(
    private val authenticationSource: AuthenticationSource
) : SendForgotPasswordUseCase {

    override suspend fun invoke(username: String): ForgotPassword {
        return authenticationSource.sendForgotPassword(username)
    }
}
