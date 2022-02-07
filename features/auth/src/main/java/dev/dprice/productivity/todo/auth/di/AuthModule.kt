package dev.dprice.productivity.todo.auth.di

import android.content.Context
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.plugin.Plugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.data.AwsAmplifySource
import dev.dprice.productivity.todo.auth.sdk.AWSAmplifySourceImpl
import dev.dprice.productivity.todo.auth.ui.signup.SignUpFormUpdater
import dev.dprice.productivity.todo.auth.ui.signup.SignUpFormUpdaterImpl
import dev.dprice.productivity.todo.auth.usecase.SignUpUserUseCase
import dev.dprice.productivity.todo.auth.usecase.SignUpUserUseCaseImpl
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
    ): AwsAmplifySource {
        return AWSAmplifySourceImpl(
            context,
            plugins,
            Dispatchers.IO
        )
    }

    @Provides
    @IntoSet
    fun provideAwsAmplifyAuthPlugin(): Plugin<*> = AWSCognitoAuthPlugin()

    @Provides
    fun provideSignUpFormUpdater() : SignUpFormUpdater = SignUpFormUpdaterImpl()

    @Provides
    fun provideSignUpUserUseCase(awsAmplifySource: AwsAmplifySource) : SignUpUserUseCase = SignUpUserUseCaseImpl(awsAmplifySource)
}