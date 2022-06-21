package dev.dprice.productivity.todo.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCase
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    @Singleton
    fun provideUpdateEntryUseCase(): UpdateEntryUseCase = UpdateEntryUseCaseImpl()
}