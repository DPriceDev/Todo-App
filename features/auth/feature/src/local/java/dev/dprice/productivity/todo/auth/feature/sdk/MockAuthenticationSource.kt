package dev.dprice.productivity.todo.auth.feature.sdk

import dev.dprice.productivity.todo.auth.library.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.library.model.Session
import dev.dprice.productivity.todo.auth.library.model.SignUpResponse
import dev.dprice.productivity.todo.auth.library.model.VerifyUserResponse
import dev.dprice.productivity.todo.core.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockAuthenticationSource @Inject constructor(): AuthenticationSource {
    var responseDelay : Long = 1000L
    var createUserResponse: SignUpResponse = SignUpResponse.Code("testUsername")
    var verifyUserResponse: VerifyUserResponse = VerifyUserResponse.Done

    override fun getCurrentSession(): Flow<DataState<Session>> = flow {
        emit(DataState.Loading)
        emit(DataState.Data(Session(false)))
    }

    override suspend fun createUser(username: String, email: String, password: String): SignUpResponse {
        delay(responseDelay)
        return createUserResponse
    }

    override suspend fun verifyNewUser(code: String, username: String): VerifyUserResponse {
        delay(responseDelay)
        return verifyUserResponse
    }

    override suspend fun resendVerificationCode() {
        delay(responseDelay)
    }
}