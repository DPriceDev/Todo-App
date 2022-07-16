package dev.dprice.productivity.todo.features.groups.feature.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.productivity.todo.features.groups.data.GroupRepository
import dev.dprice.productivity.todo.features.groups.feature.usecase.GetAllTaskGroupsUseCase
import dev.dprice.productivity.todo.features.groups.usecase.*
import dev.dprice.productivity.todo.features.tasks.usecase.GetAllTasksUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GroupUseCaseModule {

    @Provides
    @Singleton
    fun provideGetCurrentTaskGroupUseCase(
        groupRepository: GroupRepository
    ): GetCurrentGroupUseCase = GetCurrentGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideSetCurrentTaskGroupUseCase(
        groupRepository: GroupRepository
    ): SetCurrentGroupUseCase = SetCurrentGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun bindGetAllTasksUseCase(
        groupRepository: GroupRepository
    ) : GetGroupsUseCase = GetGroupsUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideCreateGroupUseCase(
        groupRepository: GroupRepository
    ): CreateGroupUseCase = CreateGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideDeleteGroupsUseCase(
        groupRepository: GroupRepository
    ): DeleteGroupsUseCase = DeleteGroupsUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideMarkGroupsAsDeletedUseCase(
        groupRepository: GroupRepository
    ): MarkGroupsAsDeletedUseCase = MarkGroupsAsDeletedUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideMarkGroupsAsNotDeletedUseCase(
        groupRepository: GroupRepository
    ): MarkGroupsAsNotDeletedUseCase = MarkGroupsAsNotDeletedUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideGetTaskGroupsUseCase(
        getAllTasksUseCase: GetAllTasksUseCase,
        getGroupsUseCase: GetGroupsUseCase
    ) : GetAllTaskGroupsUseCase =
        GetAllTaskGroupsUseCase(
            getGroupsUseCase,
            getAllTasksUseCase
        )
}