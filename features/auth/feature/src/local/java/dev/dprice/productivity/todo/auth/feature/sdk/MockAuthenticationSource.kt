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
    var createUserResponse: SignUp = SignUp.Code("testUsername")
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
        return createUserResponse
    }

    override suspend fun verifyNewUser(code: String, username: String): VerifyUser {
        delay(responseDelay)
        return verifyUser
    }

    override suspend fun resendVerificationCode(username: String): ResendCode {
        delay(responseDelay)
        return resendCode
    }

    override suspend fun signInUser(username: String, password: String): SignIn {
        delay(responseDelay)
        return when (username) {
            "no_internet" -> SignIn.NetworkError
            "disabled" -> SignIn.AccountDisabled
            "error" -> SignIn.Error(Throwable("error"))
            "code" -> SignIn.Code("code")
            else -> signIn
        }
    }

    override suspend fun sendForgotPassword(email: String): ForgotPassword {
        delay(responseDelay)
        return forgotPassword
    }

    override suspend fun resetPassword(code: String, newPassword: String): ResetPassword {
        delay(responseDelay)
        return resetPassword
    }
}
