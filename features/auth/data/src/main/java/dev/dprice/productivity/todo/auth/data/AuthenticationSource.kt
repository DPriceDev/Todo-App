package dev.dprice.productivity.todo.auth.data

import dev.dprice.productivity.todo.auth.data.model.*
import dev.dprice.productivity.todo.core.DataState
import kotlinx.coroutines.flow.Flow

interface AuthenticationSource {

    fun getCurrentSession() : Flow<DataState<Session>>

    suspend fun createUser(
        username: String,
        email: String,
        password: String
    ): SignUpResponse

    suspend fun signInUser(
        username: String,
        password: String
    ): SignInResponse

    suspend fun verifyNewUser(code: String, username: String) : VerifyUserResponse

    suspend fun resendVerificationCode(username: String) : ResendCodeResponse

    suspend fun sendForgotPassword(email: String) : ForgotPasswordResponse
}
