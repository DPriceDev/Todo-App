package dev.dprice.productivity.todo.auth.sdk

import android.content.Context
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.dprice.productivity.todo.platform.di.ApplicationCreatedListener
import timber.log.Timber
import javax.inject.Inject

class AWSAmplifyCreator @Inject constructor(
    @ApplicationContext private val context: Context
) : ApplicationCreatedListener {
    override fun onCreate() {
        try {
            Amplify.configure(context)
            Timber.d("Amplify Initialized")
        } catch (exception: AmplifyException) {
            Timber.e("Could not initialize Amplify", exception)
        }
    }
}