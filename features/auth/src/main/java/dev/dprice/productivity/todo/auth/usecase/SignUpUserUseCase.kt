package dev.dprice.productivity.todo.auth.usecase

import com.amplifyframework.auth.AuthUserAttributeKey
import dev.dprice.productivity.todo.auth.data.AwsAmplifySource
import dev.dprice.productivity.todo.auth.model.SignUpResponse
import dev.dprice.productivity.todo.auth.ui.signup.SignUpForm
import javax.inject.Inject

interface SignUpUserUseCase {

    suspend operator fun invoke(form: SignUpForm) : SignUpResponse
}

class SignUpUserUseCaseImpl @Inject constructor(
    private val awsAmplifySource: AwsAmplifySource
) : SignUpUserUseCase {

    override suspend fun invoke(form: SignUpForm) : SignUpResponse {
        // todo remove auth reference here
        val attributes = mapOf(AuthUserAttributeKey.email() to form.email.value)
        return awsAmplifySource.createUser(form.username.value, form.password.value, attributes)
    }
}