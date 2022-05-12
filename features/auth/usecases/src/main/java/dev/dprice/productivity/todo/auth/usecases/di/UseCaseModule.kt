package dev.dprice.productivity.todo.auth.usecases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.auth.usecases.updater.*

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun UpdateEntryUseCaseImpl.bindUpdateEntryUseCase(): UpdateEntryUseCase

    @Binds
    fun UpdateUsernameEntryUseCaseImpl.bindUpdateUsernameEntryUseCase(): UpdateUsernameEntryUseCase

    @Binds
    fun UpdatePasswordEntryUseCaseImpl.bindUpdatePasswordEntryUseCase(): UpdatePasswordEntryUseCase

    @Binds
    fun UpdateEmailEntryUseCaseImpl.bindUpdateEmailEntryUseCaseImpl(): UpdateEmailEntryUseCase

    @Binds
    fun UpdateCodeEntryUseCaseImpl.bindUpdateCodeEntryUseCase(): UpdateCodeEntryUseCase
}
