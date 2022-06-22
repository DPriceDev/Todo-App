package dev.dprice.productivity.todo.auth.feature.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.auth.usecases.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {

    @Provides
    @Singleton
    fun bindResendVerificationCodeUseCase(
        authenticationSource: AuthenticationSource
    ): ResendVerificationCodeUseCase = ResendVerificationCodeUseCaseImpl(authenticationSource)

    @Provides
    @Singleton
    fun bindSendForgotPasswordUseCase(
        authenticationSource: AuthenticationSource
    ): SendForgotPasswordUseCase = SendForgotPasswordUseCaseImpl(authenticationSource)

    @Provides
    @Singleton
    fun bindSignInUserUseCase(
        authenticationSource: AuthenticationSource
    ): SignInUserUseCase = SignInUserUseCaseImpl(authenticationSource)

    @Provides
    @Singleton
    fun bindSignUpUserUseCase(
        authenticationSource: AuthenticationSource
    ): SignUpUserUseCase = SignUpUserUseCaseImpl(authenticationSource)

    @Provides
    @Singleton
    fun bindVerifySignUpCodeUseCase(
        authenticationSource: AuthenticationSource
    ): VerifySignUpCodeUseCase = VerifySignUpCodeUseCaseImpl(authenticationSource)

    @Provides
    @Singleton
    fun bindResetPasswordUseCase(
        authenticationSource: AuthenticationSource
    ): ResetPasswordUseCase = ResetPasswordUseCaseImpl(authenticationSource)

    @Provides
    @Singleton
    fun bindSignOutUserUseCase(
        authenticationSource: AuthenticationSource
    ): SignOutUserUseCase = SignOutUserUseCaseImpl(authenticationSource)
}
