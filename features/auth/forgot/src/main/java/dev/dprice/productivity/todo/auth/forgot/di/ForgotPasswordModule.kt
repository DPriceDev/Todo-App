package dev.dprice.productivity.todo.auth.forgot.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.forgot.navigation.ForgotPasswordNavigationComponent
import dev.dprice.productivity.todo.auth.forgot.navigation.ResetPasswordNavigationComponent
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent

@Module
@InstallIn(SingletonComponent::class)
interface ForgotPasswordModule {

    @IntoSet
    @Binds
    fun ForgotPasswordNavigationComponent.bindForgotPasswordNavigationComponent(): AuthNavigationComponent

    @IntoSet
    @Binds
    fun ResetPasswordNavigationComponent.bindResetPasswordNavigationComponent(): AuthNavigationComponent
}
