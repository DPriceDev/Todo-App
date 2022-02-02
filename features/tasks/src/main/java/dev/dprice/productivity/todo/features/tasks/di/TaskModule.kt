package dev.dprice.productivity.todo.features.tasks.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.tasks.data.TaskRepositoryImpl
import dev.dprice.productivity.todo.features.tasks.model.TaskRepository
import dev.dprice.productivity.todo.features.tasks.usecase.GetTaskListUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.GetTaskListUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskModule {

    @Binds
    abstract fun TaskRepositoryImpl.bindTaskRepository() : TaskRepository

    @Binds
    abstract fun GetTaskListUseCaseImpl.bindGetTaskListUseCase() : GetTaskListUseCase
}