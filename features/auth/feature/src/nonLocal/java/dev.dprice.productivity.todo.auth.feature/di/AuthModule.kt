package dev.dprice.productivity.todo.auth.feature.di

import android.content.Context
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.plugin.Plugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.feature.sdk.AWSAmplifySource
import dev.dprice.productivity.todo.auth.library.data.AuthenticationSource
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideAwsAmplifySource(
        @ApplicationContext context: Context,
        plugins: Set<@JvmSuppressWildcards Plugin<*>>
    ): AuthenticationSource {
        return AWSAmplifySource(
            context,
            plugins,
            Dispatchers.IO
        )
    }

    @Provides
    @IntoSet
    fun provideAwsAmplifyAuthPlugin(): Plugin<*> = AWSCognitoAuthPlugin()
}