package dev.dprice.productivity.todo.auth.library.data

import dev.dprice.productivity.todo.auth.library.model.Session
import dev.dprice.productivity.todo.auth.library.model.SignUpResponse
import dev.dprice.productivity.todo.auth.library.model.VerifyUserResponse
import dev.dprice.productivity.todo.core.DataState
import kotlinx.coroutines.flow.Flow

interface AuthenticationSource {

    fun getCurrentSession() : Flow<DataState<Session>>

    suspend fun createUser(
        username: String,
        email: String,
        password: String
    ) : SignUpResponse

    suspend fun verifyNewUser(code: String, username: String) : VerifyUserResponse
}