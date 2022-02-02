package dev.dprice.productivity.todo.features.task.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.task.data.TaskRepositoryImpl
import dev.dprice.productivity.todo.features.task.model.TaskRepository
import dev.dprice.productivity.todo.features.task.usecase.GetTaskListUseCase
import dev.dprice.productivity.todo.features.task.usecase.GetTaskListUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskModule {

    @Binds
    abstract fun TaskRepositoryImpl.bindTaskRepository() : TaskRepository

    @Binds
    abstract fun GetTaskListUseCaseImpl.bindGetTaskListUseCase() : GetTaskListUseCase
}