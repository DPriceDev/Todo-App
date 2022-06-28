package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.GroupRepository
import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.data.model.TaskGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAllTaskGroupsUseCase(
    private val groupRepository: GroupRepository,
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<List<TaskGroup>> = groupRepository
        .getGroups()
        .combine(taskRepository.getTasks()) { groups, tasks ->
            tasks.groupBy { task -> groups.find { it.id == task.groupId } }
                .map { (group, tasks) -> TaskGroup(group, tasks) }
        }
}