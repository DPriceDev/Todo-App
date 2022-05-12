package dev.dprice.productivity.todo.auth.signin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.signin.navigation.ForgotPasswordNavigationComponent
import dev.dprice.productivity.todo.auth.signin.navigation.ResetPasswordNavigationComponent
import dev.dprice.productivity.todo.auth.signin.navigation.SignInAuthNavigationComponent
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SignInModule {

    @IntoSet
    @Binds
    abstract fun SignInAuthNavigationComponent.bindSignInAuthNavigationComponent(): AuthNavigationComponent

    @IntoSet
    @Binds
    abstract fun ForgotPasswordNavigationComponent.bindForgotPasswordNavigationComponent(): AuthNavigationComponent

    @IntoSet
    @Binds
    abstract fun ResetPasswordNavigationComponent.bindResetPasswordNavigationComponent(): AuthNavigationComponent
}
