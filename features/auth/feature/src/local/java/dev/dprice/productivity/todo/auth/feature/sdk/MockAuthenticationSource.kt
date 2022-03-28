package dev.dprice.productivity.todo.auth.feature.sdk

import dev.dprice.productivity.todo.auth.library.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.library.model.Session
import dev.dprice.productivity.todo.auth.library.model.SignUpResponse
import dev.dprice.productivity.todo.auth.library.model.VerifyUserResponse
import dev.dprice.productivity.todo.core.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockAuthenticationSource @Inject constructor(): AuthenticationSource {

    override fun getCurrentSession(): Flow<DataState<Session>> = flow {
        emit(DataState.Loading)
        emit(DataState.Data(Session(false)))
    }

    override suspend fun createUser(username: String, email: String, password: String): SignUpResponse {
       return SignUpResponse.Code("testUsername")
    }

    override suspend fun verifyNewUser(code: String, username: String): VerifyUserResponse {
        return VerifyUserResponse.Done
    }
}