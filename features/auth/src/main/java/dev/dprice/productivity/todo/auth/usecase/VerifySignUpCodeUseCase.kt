package dev.dprice.productivity.todo.auth.usecase

import dev.dprice.productivity.todo.auth.data.AwsAmplifySource

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