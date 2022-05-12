package dev.dprice.productivity.todo.auth.feature.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.navigation.ForgotPasswordNavigationComponent
import dev.dprice.productivity.todo.auth.feature.screens.landing.navigation.LandingAuthNavigationComponent
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.navigation.ResetPasswordNavigationComponent
import dev.dprice.productivity.todo.auth.feature.screens.signin.navigation.SignInAuthNavigationComponent
import dev.dprice.productivity.todo.auth.feature.screens.signup.navigation.SignUpAuthNavigationComponent
import dev.dprice.productivity.todo.auth.feature.screens.verify.navigation.VerifyAuthNavigationComponent
import dev.dprice.productivity.todo.auth.feature.ui.navigation.AuthNavigationComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthNavigationModule {

    @IntoSet
    @Binds
    fun LandingAuthNavigationComponent.bindLandingAuthNavigationComponent(): AuthNavigationComponent

    @IntoSet
    @Binds
    fun SignInAuthNavigationComponent.bindSignInAuthNavigationComponent(): AuthNavigationComponent

    @Binds
    @IntoSet
    fun SignUpAuthNavigationComponent.bindSignUpAuthNavigationComponent(): AuthNavigationComponent

    @IntoSet
    @Binds
    fun VerifyAuthNavigationComponent.bindVerifyAuthNavigationComponent(): AuthNavigationComponent

    @IntoSet
    @Binds
    fun ForgotPasswordNavigationComponent.bindForgotPasswordNavigationComponent(): AuthNavigationComponent

    @IntoSet
    @Binds
    fun ResetPasswordNavigationComponent.bindResetPasswordNavigationComponent(): AuthNavigationComponent
}
