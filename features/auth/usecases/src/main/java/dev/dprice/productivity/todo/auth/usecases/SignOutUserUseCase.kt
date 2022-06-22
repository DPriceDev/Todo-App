package dev.dprice.productivity.todo.auth.usecases

import dev.dprice.productivity.todo.auth.data.AuthenticationSource

interface SignOutUserUseCase {
    suspend operator fun invoke()
}

class SignOutUserUseCaseImpl(
    private val authenticationSource: AuthenticationSource
) : SignOutUserUseCase {

    override suspend operator fun invoke() {
        authenticationSource.signOutUser()
    }
}
