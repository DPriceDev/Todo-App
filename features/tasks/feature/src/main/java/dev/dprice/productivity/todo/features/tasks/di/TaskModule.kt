package dev.dprice.productivity.todo.features.tasks.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskDetailsEntryUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskDetailsEntryUseCaseImpl
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskTitleEntryUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskTitleEntryUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TaskModule {

    @Binds
    @Singleton
    fun UpdateTaskTitleEntryUseCaseImpl.bindUpdateTaskTitleEntryUseCase(): UpdateTaskTitleEntryUseCase

    @Binds
    @Singleton
    fun UpdateTaskDetailsEntryUseCaseImpl.bindUpdateTaskDetailsEntryUseCase(): UpdateTaskDetailsEntryUseCase

    // todo: task api
}