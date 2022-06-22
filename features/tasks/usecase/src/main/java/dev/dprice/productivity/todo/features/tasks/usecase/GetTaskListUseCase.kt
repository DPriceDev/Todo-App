package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetTaskListUseCase {
     operator fun invoke() : Flow<List<Task>>
}

class GetTaskListUseCaseImpl(private val taskRepository: TaskRepository): GetTaskListUseCase {
    override operator fun invoke() = taskRepository
        .getTasks()
        .map { tasks ->
            tasks
                .sortedBy { it.dateTime }
                .sortedBy { it.isComplete }
        }
}