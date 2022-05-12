package dev.dprice.productivity.todo.auth.signup.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.signup.navigation.SignUpAuthNavigationComponent
import dev.dprice.productivity.todo.auth.usecases.auth.SignUpUserUseCase
import dev.dprice.productivity.todo.auth.usecases.auth.SignUpUserUseCaseImpl
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SignUpModule {

    @Binds
    @IntoSet
    abstract fun SignUpAuthNavigationComponent.bindSignUpAuthNavigationComponent(): AuthNavigationComponent
}