package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.GroupRepository
import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

interface GetCurrentTasksUseCase {
     operator fun invoke() : Flow<List<Task>>
}

class GetCurrentTasksUseCaseImpl(
    private val taskRepository: TaskRepository,
    private val groupRepository: GroupRepository
): GetCurrentTasksUseCase {
    override operator fun invoke() = taskRepository
        .getTasks()
        .combine(groupRepository.getCurrentGroup()) { tasks, group ->
            tasks.filter { it.groupId == group?.id }
        }
        .map { tasks ->
            tasks.sortedBy { it.finishDate }
                .sortedBy { it.isCompleted }
        }
}