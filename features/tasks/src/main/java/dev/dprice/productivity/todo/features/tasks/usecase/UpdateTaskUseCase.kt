package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import javax.inject.Inject

interface UpdateTaskUseCase {
    suspend operator fun invoke(task: Task)
}

class UpdateTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
): UpdateTaskUseCase {
    override suspend fun invoke(task: Task) = taskRepository.updateTask(task)
}