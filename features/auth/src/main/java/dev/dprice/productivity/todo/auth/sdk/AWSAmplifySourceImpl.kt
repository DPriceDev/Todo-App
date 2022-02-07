package dev.dprice.productivity.todo.auth.sdk

import android.content.Context
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthSession
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.step.AuthSignUpStep
import com.amplifyframework.core.plugin.Plugin
import com.amplifyframework.kotlin.core.Amplify
import dev.dprice.productivity.todo.auth.data.AwsAmplifySource
import dev.dprice.productivity.todo.auth.model.SignUpResponse
import dev.dprice.productivity.todo.core.DataState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class AWSAmplifySourceImpl(
    private val context: Context,
    private val plugins: Set<Plugin<*>>,
    private val ioDispatcher: CoroutineDispatcher
) : AwsAmplifySource {

    private var auth: Deferred<Boolean> = CoroutineScope(ioDispatcher).async {
        initializeAmplify()
    }

    override fun getCurrentSession() : Flow<DataState<AuthSession>> = flow {
        if(auth.await()) {
            emit(DataState.Loading)
            val session = Amplify.Auth.fetchAuthSession()
            emit(DataState.Data(session))
        } else {
            emit(DataState.Error(AwsAmplifyNotInitializedException()))
        }
    }

    override suspend fun createUser(
        username: String,
        password: String,
        attributes: Map<AuthUserAttributeKey, String>
    ): SignUpResponse = try {
        val attributesMap = attributes.map { AuthUserAttribute(it.key, it.value) }
        val signUpOptions = AuthSignUpOptions.builder()
            .userAttributes(attributesMap)
            .build()

        val result = Amplify.Auth.signUp(username, password, signUpOptions)
        Timber.d("Sign up returned: $result")
        when (result.nextStep.signUpStep) {
            AuthSignUpStep.CONFIRM_SIGN_UP_STEP -> SignUpResponse.Code(username)
            AuthSignUpStep.DONE -> SignUpResponse.Done
        }
    } catch (throwable: AuthException) {
        Timber.e(throwable, "Sign up failed for user: $username")
        SignUpResponse.Error(throwable)
    }

    override suspend fun verifyNewUser(
        code: String,
        username: String,
        attributes: List<AuthUserAttribute>
    ) : Boolean {
        return try {
            val result = Amplify.Auth.confirmSignUp(username, code)
            Timber.i("AuthQuickstart", "Confirmed signin: $result")
            true
        } catch (error: AuthException) {
            Timber.e("AuthQuickstart", "Failed to confirm signin", error)
            false
        }
    }

    // todo split out? make this an auth source only?
    private fun initializeAmplify(): Boolean {
        return try {
            plugins.forEach { Amplify.addPlugin(it) }
            Amplify.configure(context)
            Timber.d("Amplify Initialized")
            true
        } catch (exception: AmplifyException) {
            Timber.e("Could not initialize Amplify", exception)
            false
        }
    }

}