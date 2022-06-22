package dev.dprice.productivity.todo.auth.feature.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.auth.feature.usecase.*

@Module
@InstallIn(SingletonComponent::class)
interface EntryUseCaseModule {

    @Binds
    fun UpdateUsernameEntryUseCaseImpl.bindUpdateUsernameEntryUseCase(): UpdateUsernameEntryUseCase

    @Binds
    fun UpdatePasswordEntryUseCaseImpl.bindUpdatePasswordEntryUseCase(): UpdatePasswordEntryUseCase

    @Binds
    fun UpdateEmailEntryUseCaseImpl.bindUpdateEmailEntryUseCaseImpl(): UpdateEmailEntryUseCase

    @Binds
    fun UpdateCodeEntryUseCaseImpl.bindUpdateCodeEntryUseCase(): UpdateCodeEntryUseCase
}
