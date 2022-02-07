package dev.dprice.productivity.todo.auth.library.usecase

import dev.dprice.productivity.todo.auth.library.data.AwsAmplifySource

interface VerifySignUpCodeUseCase {

    suspend operator fun invoke()
}

class VerifySignUpCodeUseCaseImpl(
    private val awsAmplifySource: AwsAmplifySource
) : VerifySignUpCodeUseCase {

    override suspend fun invoke() {
        TODO("Not yet implemented")
    }
}