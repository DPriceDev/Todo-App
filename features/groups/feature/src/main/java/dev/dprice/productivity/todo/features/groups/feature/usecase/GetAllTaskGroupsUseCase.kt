package dev.dprice.productivity.todo.features.groups.feature.usecase

import dev.dprice.productivity.todo.features.groups.feature.model.TaskGroup
import dev.dprice.productivity.todo.features.groups.usecase.GetGroupsUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.GetAllTasksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAllTaskGroupsUseCase(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase
) {
    operator fun invoke(): Flow<List<TaskGroup>> = getGroupsUseCase()
        .combine(getAllTasksUseCase()) { groups, tasks ->
            groups
                .filter { !it.isDeleted }
                .associateWith { group -> tasks.filter { it.groupId == group.id } }
                .map { (group, tasks) -> TaskGroup(group, tasks) }
                .let { taskGroups ->
                    if (taskGroups.find { it.group == null } == null) {
                        taskGroups.plus(TaskGroup(null, emptyList()))
                    } else {
                        taskGroups
                    }
                }
                .sortedBy { it.group?.name }
        }
}