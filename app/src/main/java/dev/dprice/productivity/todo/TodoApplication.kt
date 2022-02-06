package dev.dprice.productivity.todo

import android.app.Application
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.kotlin.core.Amplify
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TodoApplication @Inject constructor() : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Timber.d("Amplify Initialized")
        } catch (exception: AmplifyException) {
            Timber.e("Could not initialize Amplify", exception)
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val session = Amplify.Auth.fetchAuthSession()
                Timber.d("AAuth session = $session")
            } catch (error: AuthException) {
                Timber.e("AmplifyQuickstart", "auth session", error)
            }
        }
    }
}