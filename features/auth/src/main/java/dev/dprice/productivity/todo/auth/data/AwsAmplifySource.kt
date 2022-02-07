package dev.dprice.productivity.todo.auth.data

import com.amplifyframework.auth.AuthSession
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import dev.dprice.productivity.todo.auth.model.SignUpResponse
import dev.dprice.productivity.todo.core.DataState
import kotlinx.coroutines.flow.Flow

interface AwsAmplifySource {

    fun getCurrentSession() : Flow<DataState<AuthSession>>

    suspend fun createUser(
        username: String,
        password: String,
        attributes: Map<AuthUserAttributeKey, String>
    ) : SignUpResponse

    suspend fun verifyNewUser(
        code: String,
        username: String,
        attributes: List<AuthUserAttribute>
    ) : Boolean
}