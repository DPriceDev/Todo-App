package dev.dprice.productivity.todo.features.tasks.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.tasks.data.TaskRepositoryImpl
import dev.dprice.productivity.todo.features.tasks.data.TaskService
import dev.dprice.productivity.todo.features.tasks.data.TaskServiceImpl
import dev.dprice.productivity.todo.features.tasks.model.TaskRepository
import dev.dprice.productivity.todo.features.tasks.usecase.GetTaskListUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.GetTaskListUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
class TaskModule {

    @Provides
    fun provideTaskRepository(taskService: TaskService) : TaskRepository = TaskRepositoryImpl(taskService)

    @Provides
    fun provideGetTaskListUseCase(taskRepository: TaskRepository) : GetTaskListUseCase {
        return GetTaskListUseCaseImpl(taskRepository)
    }

    @Provides
    fun provideTaskService() : TaskService = TaskServiceImpl()

    // todo: task api
}