package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.groups.usecase.GetCurrentGroupUseCase
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

interface GetCurrentTasksUseCase {
     operator fun invoke() : Flow<List<Task>>
}

class GetCurrentTasksUseCaseImpl(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getCurrentGroupUseCase: GetCurrentGroupUseCase
): GetCurrentTasksUseCase {
    override operator fun invoke() = getAllTasksUseCase()
        .combine(getCurrentGroupUseCase()) { tasks, group ->
            tasks.filter { it.groupId == group?.id }
        }
        .map { tasks ->
            tasks.sortedBy { it.finishDate }
                .sortedBy { it.isCompleted }
        }
}