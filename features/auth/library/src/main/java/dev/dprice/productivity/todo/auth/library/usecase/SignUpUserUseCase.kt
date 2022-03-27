package dev.dprice.productivity.todo.auth.library.usecase

import com.amplifyframework.auth.AuthUserAttributeKey
import dev.dprice.productivity.todo.auth.library.data.AwsAmplifySource
import dev.dprice.productivity.todo.auth.library.model.SignUpResponse
import javax.inject.Inject

interface SignUpUserUseCase {

    suspend operator fun invoke(
        username: String,
        email: String,
        password: String
    ) : SignUpResponse
}

class SignUpUserUseCaseImpl @Inject constructor(
    private val awsAmplifySource: AwsAmplifySource
) : SignUpUserUseCase {

    override suspend fun invoke(
        username: String,
        email: String,
        password: String
    ) : SignUpResponse {
        // todo remove auth reference here
        val attributes = mapOf(AuthUserAttributeKey.email() to email)
        return awsAmplifySource.createUser(username, password, attributes)
    }
}