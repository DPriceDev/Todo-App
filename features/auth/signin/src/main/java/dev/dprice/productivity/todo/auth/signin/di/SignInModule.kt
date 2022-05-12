package dev.dprice.productivity.todo.auth.signin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.dprice.productivity.todo.auth.usecases.SendForgotPasswordUseCase
import dev.dprice.productivity.todo.auth.usecases.SendForgotPasswordUseCaseImpl
import dev.dprice.productivity.todo.auth.usecases.SignInUserUseCase
import dev.dprice.productivity.todo.auth.usecases.SignInUserUseCaseImpl
import dev.dprice.productivity.todo.auth.signin.navigation.ForgotPasswordNavigationComponent
import dev.dprice.productivity.todo.auth.signin.navigation.ResetPasswordNavigationComponent
import dev.dprice.productivity.todo.auth.signin.navigation.SignInAuthNavigationComponent
import dev.dprice.productivity.todo.auth.signin.ui.forgot.VerifyEmailEntryUseCase
import dev.dprice.productivity.todo.auth.signin.ui.forgot.VerifyEmailEntryUseCaseImpl
import dev.dprice.productivity.todo.auth.signin.ui.reset.UpdateCodeEntryUseCase
import dev.dprice.productivity.todo.auth.signin.ui.reset.UpdateCodeEntryUseCaseImpl
import dev.dprice.productivity.todo.auth.signin.ui.reset.UpdatePasswordEntryUseCase
import dev.dprice.productivity.todo.auth.signin.ui.reset.UpdatePasswordEntryUseCaseImpl
import dev.dprice.productivity.todo.auth.signin.ui.signin.SignInFormUpdater
import dev.dprice.productivity.todo.auth.signin.ui.signin.SignInFormUpdaterImpl
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

    @Binds
    abstract fun SignInUserUseCaseImpl.bindSignInUserUseCase(): SignInUserUseCase

    @Binds
    abstract fun SignInFormUpdaterImpl.bindSignInFormUpdater(): SignInFormUpdater

    @Binds
    abstract fun VerifyEmailEntryUseCaseImpl.bindVerifyEmailEntryUseCase(): VerifyEmailEntryUseCase

    @Binds
    abstract fun SendForgotPasswordUseCaseImpl.bindSendForgotPasswordUseCase(): SendForgotPasswordUseCase

    @Binds
    abstract fun UpdateCodeEntryUseCaseImpl.bindUpdateCodeEntryUseCase(): UpdateCodeEntryUseCase

    @Binds
    abstract fun UpdatePasswordEntryUseCaseImpl.bindUpdatePasswordEntryUseCase(): UpdatePasswordEntryUseCase
}
