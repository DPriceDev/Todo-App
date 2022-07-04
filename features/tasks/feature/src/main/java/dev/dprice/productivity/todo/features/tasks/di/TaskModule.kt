package dev.dprice.productivity.todo.features.tasks.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskDetailsEntryUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskTitleEntryUseCase
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    @Singleton
    fun provideUpdateTaskTitleEntryUseCase(updateEntryUseCase: UpdateEntryUseCase): UpdateTaskTitleEntryUseCase {
        return UpdateTaskTitleEntryUseCase(updateEntryUseCase)
    }

    @Provides
    @Singleton
    fun provideUpdateTaskDetailsEntryUseCase(updateEntryUseCase: UpdateEntryUseCase): UpdateTaskDetailsEntryUseCase {
        return UpdateTaskDetailsEntryUseCase(updateEntryUseCase)
    }

    // todo: task api
}