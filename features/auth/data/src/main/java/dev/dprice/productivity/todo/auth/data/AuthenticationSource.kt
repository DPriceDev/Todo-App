package dev.dprice.productivity.todo.auth.data

import dev.dprice.productivity.todo.auth.data.model.*
import dev.dprice.productivity.todo.core.DataState
import kotlinx.coroutines.flow.Flow

interface AuthenticationSource {

    fun getCurrentSession(): Flow<DataState<SessionThirteen>>

    suspend fun createUser(
        username: String,
        email: String,
        password: String
    ): SignUp

    suspend fun signInUser(
        username: String,
        password: String
    ): SignIn

    suspend fun verifyNewUser(code: String, username: String): VerifyUser

    suspend fun resendVerificationCode(username: String): ResendCode

    suspend fun sendForgotPassword(email: String): ForgotPasswordThirteen
}
