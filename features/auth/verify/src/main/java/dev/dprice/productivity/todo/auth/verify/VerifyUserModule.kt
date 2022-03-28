package dev.dprice.productivity.todo.auth.verify

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.auth.library.usecase.VerifySignUpCodeUseCase
import dev.dprice.productivity.todo.auth.library.usecase.VerifySignUpCodeUseCaseImpl
import dev.dprice.productivity.todo.auth.verify.viewmodel.VerifyUserCodeUpdater
import dev.dprice.productivity.todo.auth.verify.viewmodel.VerifyUserCodeUpdaterImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class VerifyUserModule {

    @Binds
    abstract fun VerifyUserCodeUpdaterImpl.bindVerifyUserCodeUpdater() : VerifyUserCodeUpdater

    @Binds
    abstract fun VerifySignUpCodeUseCaseImpl.bindVerifySignUpCodeUseCase() : VerifySignUpCodeUseCase
}