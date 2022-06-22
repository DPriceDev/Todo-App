package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.ResetPassword

interface ResetPasswordUseCase {
    suspend operator fun invoke(code: String, newPassword: String): ResetPassword
}

class ResetPasswordUseCaseImpl(
    private val authenticationSource: AuthenticationSource
) : ResetPasswordUseCase {

    override suspend fun invoke(code: String, newPassword: String): ResetPassword {
        return authenticationSource.resetPassword(code, newPassword)
    }
}
