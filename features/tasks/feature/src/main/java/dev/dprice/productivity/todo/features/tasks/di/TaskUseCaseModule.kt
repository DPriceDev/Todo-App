package dev.dprice.productivity.todo.features.tasks.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.groups.usecase.GetCurrentGroupUseCase
import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.usecase.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TaskUseCaseModule {

    @Provides
    @Singleton
    fun bindAddTaskUseCase(
        taskRepository: TaskRepository
    ) : AddTaskUseCase = AddTaskUseCaseImpl(taskRepository)

    @Provides
    @Singleton
    fun bindGetTaskListUseCase(
        getAllTasksUseCase: GetAllTasksUseCase,
        getCurrentGroupUseCase: GetCurrentGroupUseCase
    ) : GetCurrentTasksUseCase = GetCurrentTasksUseCaseImpl(
        getAllTasksUseCase,
        getCurrentGroupUseCase
    )

    @Provides
    @Singleton
    fun bindGetAllTasksUseCase(
        taskRepository: TaskRepository
    ) : GetAllTasksUseCase = GetAllTasksUseCase(taskRepository)

    @Provides
    @Singleton
    fun bindDeleteTaskUseCase(
        taskRepository: TaskRepository
    ) : DeleteTaskUseCase = DeleteTaskUseCaseImpl(taskRepository)

    @Provides
    @Singleton
    fun bindUpdateTaskUseCase(
        taskRepository: TaskRepository
    ) : UpdateTaskUseCase = UpdateTaskUseCaseImpl(taskRepository)
}