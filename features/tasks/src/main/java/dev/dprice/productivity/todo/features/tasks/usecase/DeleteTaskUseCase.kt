package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import javax.inject.Inject

interface DeleteTaskUseCase {
    suspend operator fun invoke(id: String)
}

class DeleteTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
): DeleteTaskUseCase {
    override suspend operator fun invoke(id: String) = taskRepository.deleteTask(id)
}