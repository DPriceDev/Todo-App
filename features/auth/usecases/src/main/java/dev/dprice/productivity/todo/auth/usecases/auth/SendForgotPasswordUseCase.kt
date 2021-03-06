package dev.dprice.productivity.todo.auth.usecases.auth

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.ForgotPassword
import javax.inject.Inject

interface SendForgotPasswordUseCase {
    suspend operator fun invoke(username: String): ForgotPassword
}

class SendForgotPasswordUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : SendForgotPasswordUseCase {

    override suspend fun invoke(username: String): ForgotPassword {
        return authenticationSource.sendForgotPassword(username)
    }
}
