package dev.dprice.productivity.todo.features.tasks.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.tasks.data.GroupRepository
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
        taskRepository: TaskRepository,
        groupRepository: GroupRepository
    ) : GetCurrentTasksUseCase = GetCurrentTasksUseCaseImpl(
        taskRepository,
        groupRepository
    )

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

    @Provides
    @Singleton
    fun provideGetTaskGroupsUseCase(
        groupRepository: GroupRepository,
        taskRepository: TaskRepository
    ) : GetAllTaskGroupsUseCase = GetAllTaskGroupsUseCase(groupRepository, taskRepository)

    @Provides
    @Singleton
    fun provideGetCurrentTaskGroupUseCase(
        groupRepository: GroupRepository
    ) : GetCurrentGroupUseCase = GetCurrentGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideSetCurrentTaskGroupUseCase(
        groupRepository: GroupRepository
    ) : SetCurrentGroupUseCase = SetCurrentGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideCreateGroupUseCase(
        groupRepository: GroupRepository
    ) : CreateGroupUseCase = CreateGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideDeleteGroupsUseCase(
        groupRepository: GroupRepository
    ) : DeleteGroupsUseCase = DeleteGroupsUseCase(groupRepository)
}