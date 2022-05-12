package dev.dprice.productivity.todo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.core.DataState
import dev.dprice.productivity.todo.platform.di.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TodoApplication : Application() {

    @Inject
    lateinit var authenticationSource: AuthenticationSource

    @Inject
    @IO
    lateinit var ioDispatcher: CoroutineDispatcher

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        CoroutineScope(ioDispatcher).launch {
            authenticationSource.getCurrentSession().collect {
                when (it) {
                    is DataState.Data -> Timber.d("AAuth session = ${ it.value }")
                    is DataState.Error -> Timber.e("Amplify session error: ${ it.throwable }")
                    DataState.Loading -> Timber.d("Amplify Loading")
                }
            }
        }
    }
}
