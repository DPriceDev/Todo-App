package dev.dprice.productivity.todo.auth.feature.sdk

import android.content.Context
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.step.AuthSignInStep
import com.amplifyframework.auth.result.step.AuthSignUpStep
import com.amplifyframework.core.plugin.Plugin
import com.amplifyframework.kotlin.core.Amplify
import dev.dprice.productivity.todo.auth.library.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.library.model.*
import dev.dprice.productivity.todo.core.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class AWSAmplifySource(
    private val context: Context,
    private val plugins: Set<Plugin<*>>,
    private val ioDispatcher: CoroutineDispatcher
) : AuthenticationSource {

    private var auth: Deferred<Boolean> = CoroutineScope(ioDispatcher).async {
        initializeAmplify()
    }

    override fun getCurrentSession() : Flow<DataState<Session>> = flow {
        if(auth.await()) {
            emit(DataState.Loading)
            val session = Amplify.Auth.fetchAuthSession()
            emit(DataState.Data(Session(session.isSignedIn)))
        } else {
            emit(DataState.Error(AwsAmplifyNotInitializedException()))
        }
    }

    override suspend fun createUser(
        username: String,
        email: String,
        password: String
    ): SignUpResponse = try {
        val attributes = listOf(
            AuthUserAttribute(AuthUserAttributeKey.email(), email)
        )
        val signUpOptions = AuthSignUpOptions.builder()
            .userAttributes(attributes)
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

    override suspend fun signInUser(username: String, password: String): SignInResponse {
        return try {

            val result = Amplify.Auth.signIn(username, password)
            when(result.nextStep.signInStep) {
                AuthSignInStep.CONFIRM_SIGN_UP -> SignInResponse.Code(username)
                AuthSignInStep.DONE -> SignInResponse.Done
                AuthSignInStep.CONFIRM_SIGN_IN_WITH_SMS_MFA_CODE -> TODO()
                AuthSignInStep.CONFIRM_SIGN_IN_WITH_CUSTOM_CHALLENGE -> TODO()
                AuthSignInStep.CONFIRM_SIGN_IN_WITH_NEW_PASSWORD -> TODO()
                AuthSignInStep.RESET_PASSWORD -> TODO()
            }
        } catch (throwable: AuthException) {
            SignInResponse.Error(throwable)
        }
    }

    override suspend fun verifyNewUser(code: String, username: String) : VerifyUserResponse {
        return try {
            val result = Amplify.Auth.confirmSignUp(username, code)
            Timber.i("AuthQuickstart", "Confirmed signin: $result")
            VerifyUserResponse.Done
        } catch (error: AuthException) {
            Timber.e("AuthQuickstart", "Failed to confirm signin", error)
            VerifyUserResponse.Error(error)
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

    override suspend fun resendVerificationCode(username: String) : ResendCodeResponse {
        return try {
            Amplify.Auth.resendSignUpCode(username)
            ResendCodeResponse.Done
        } catch (throwable: Throwable) {
            ResendCodeResponse.Error(throwable)
        }
    }

    // todo: might need to be username?
    override suspend fun sendForgotPassword(email: String): ForgotPasswordResponse {
        return try {
            Amplify.Auth.resetPassword(email)
            ForgotPasswordResponse.Done
        } catch (throwable: Throwable) {
            ForgotPasswordResponse.Error(throwable)
        }
    }

    // todo: verify forgot password code?
}