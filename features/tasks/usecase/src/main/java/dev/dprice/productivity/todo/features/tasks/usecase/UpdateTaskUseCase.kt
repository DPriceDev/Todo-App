package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Task

interface UpdateTaskUseCase {
    suspend operator fun invoke(task: Task)
}

class UpdateTaskUseCaseImpl(private val taskRepository: TaskRepository): UpdateTaskUseCase {
    override suspend fun invoke(task: Task) = taskRepository.updateTask(task)
}