package dev.dprice.productivity.todo.auth.verify.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.library.usecase.ResendVerificationCodeUseCase
import dev.dprice.productivity.todo.auth.library.usecase.ResendVerificationCodeUseCaseImpl
import dev.dprice.productivity.todo.auth.verify.navigation.VerifyAuthNavigationComponent
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class VerifyModule {

    @IntoSet
    @Binds
    abstract fun VerifyAuthNavigationComponent.bindVerifyAuthNavigationComponent() : AuthNavigationComponent

    @Binds
    abstract fun ResendVerificationCodeUseCaseImpl.bindResendVerificationCodeUseCase() : ResendVerificationCodeUseCase
}