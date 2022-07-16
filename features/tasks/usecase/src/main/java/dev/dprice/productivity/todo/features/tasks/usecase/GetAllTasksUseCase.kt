package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.TaskRepository

class GetAllTasksUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke() = taskRepository.getTasks()
}