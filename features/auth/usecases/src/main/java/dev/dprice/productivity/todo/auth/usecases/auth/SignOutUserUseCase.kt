package dev.dprice.productivity.todo.auth.usecases.auth

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import javax.inject.Inject

interface SignOutUserUseCase {
    suspend operator fun invoke()
}

class SignOutUserUseCaseImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : SignOutUserUseCase {

    override suspend operator fun invoke() {
        authenticationSource.signOutUser()
    }
}
