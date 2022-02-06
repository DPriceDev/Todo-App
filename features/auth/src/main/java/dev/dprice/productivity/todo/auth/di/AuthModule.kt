package dev.dprice.productivity.todo.auth.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.sdk.AWSAmplifyCreator
import dev.dprice.productivity.todo.auth.ui.signup.SignUpFormUpdater
import dev.dprice.productivity.todo.auth.ui.signup.SignUpFormUpdaterImpl
import dev.dprice.productivity.todo.auth.usecase.SignUpUserUseCase
import dev.dprice.productivity.todo.auth.usecase.SignUpUserUseCaseImpl
import dev.dprice.productivity.todo.platform.di.ApplicationCreatedListener

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @IntoSet
    fun provideAWSAmplifyCreator(@ApplicationContext context: Context): ApplicationCreatedListener {
        return AWSAmplifyCreator(context)
    }

    @Provides
    fun provideSignUpFormUpdater() : SignUpFormUpdater = SignUpFormUpdaterImpl()

    @Provides
    fun provideSignUpUserUseCase() : SignUpUserUseCase = SignUpUserUseCaseImpl()
}