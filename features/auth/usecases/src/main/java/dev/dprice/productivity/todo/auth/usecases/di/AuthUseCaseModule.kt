package dev.dprice.productivity.todo.auth.usecases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.auth.usecases.auth.*

@Module
@InstallIn(SingletonComponent::class)
interface AuthUseCaseModule {

    @Binds
    fun ResendVerificationCodeUseCaseImpl.bindResendVerificationCodeUseCase(): ResendVerificationCodeUseCase

    @Binds
    fun SendForgotPasswordUseCaseImpl.bindSendForgotPasswordUseCase(): SendForgotPasswordUseCase

    @Binds
    fun SignInUserUseCaseImpl.bindSignInUserUseCase(): SignInUserUseCase

    @Binds
    fun SignUpUserUseCaseImpl.bindSignUpUserUseCase(): SignUpUserUseCase

    @Binds
    fun VerifySignUpCodeUseCaseImpl.bindVerifySignUpCodeUseCase(): VerifySignUpCodeUseCase
}
