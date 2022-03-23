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
import dev.dprice.productivity.todo.auth.feature.sdk.AWSAmplifySourceImpl
import dev.dprice.productivity.todo.auth.library.data.AwsAmplifySource
import dev.dprice.productivity.todo.auth.library.usecase.SignUpUserUseCase
import dev.dprice.productivity.todo.auth.library.usecase.SignUpUserUseCaseImpl
import dev.dprice.productivity.todo.auth.signup.viewmodel.SignUpFormUpdater
import dev.dprice.productivity.todo.auth.signup.viewmodel.SignUpFormUpdaterImpl
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