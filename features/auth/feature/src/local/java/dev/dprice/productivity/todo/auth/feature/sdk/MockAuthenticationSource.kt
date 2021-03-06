package dev.dprice.productivity.todo.auth.feature.sdk

import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.data.model.*
import dev.dprice.productivity.todo.core.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockAuthenticationSource @Inject constructor() : AuthenticationSource {
    var responseDelay: Long = 1000L
    var signUp: SignUp = SignUp.Done
    var verifyUser: VerifyUser = VerifyUser.Done
    var resendCode: ResendCode = ResendCode.Done
    var signIn: SignIn = SignIn.Done
    var forgotPassword: ForgotPassword = ForgotPassword.Done
    var resetPassword: ResetPassword = ResetPassword.Done

    override fun getCurrentSession(): Flow<DataState<Session>> = flow {
        emit(DataState.Loading)
        emit(DataState.Data(Session(false)))
    }

    override suspend fun createUser(username: String, email: String, password: String): SignUp {
        delay(responseDelay)
        return when (username) {
            "no_internet" -> SignUp.ConnectionError
            "error" -> SignUp.Error(Throwable("error"))
            "exists" -> SignUp.UsernameExists(username)
            "code" -> SignUp.Code(username)
            else -> signUp
        }
    }

    override suspend fun verifyNewUser(code: String, username: String): VerifyUser {
        delay(responseDelay)
        return when (code) {
            "111222" -> VerifyUser.Error(Throwable("error"))
            "222333" -> VerifyUser.ConnectionError
            "333444" -> VerifyUser.ExpiredCode
            "444555" -> VerifyUser.IncorrectCode
            else -> verifyUser
        }
    }

    override suspend fun resendVerificationCode(username: String): ResendCode {
        delay(responseDelay)
        return when (username) {
            "resend_no_internet" -> ResendCode.ConnectionError
            "resend_error" -> ResendCode.Error(Throwable())
            else -> resendCode
        }
    }

    override suspend fun signInUser(username: String, password: String): SignIn {
        delay(responseDelay)
        return when (username) {
            "no_internet" -> SignIn.ConnectionError
            "disabled" -> SignIn.AccountDisabled
            "error" -> SignIn.Error(Throwable("error"))
            "code" -> SignIn.Code(username)
            else -> signIn
        }
    }

    override suspend fun sendForgotPassword(email: String): ForgotPassword {
        delay(responseDelay)
        return when (email) {
            "error@test.com" -> ForgotPassword.Error(Throwable("error"))
            "no_internet@test.com" -> ForgotPassword.ConnectionError
            else -> forgotPassword
        }
    }

    override suspend fun resetPassword(code: String, newPassword: String): ResetPassword {
        delay(responseDelay)
        return when (code) {
            "111222" -> ResetPassword.Error(Throwable("error"))
            "222333" -> ResetPassword.ConnectionError
            else -> resetPassword
        }
    }
}
