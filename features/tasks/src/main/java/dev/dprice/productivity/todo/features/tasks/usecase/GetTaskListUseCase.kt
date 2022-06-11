package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.TaskRepository

interface GetTaskListUseCase {
    suspend operator fun invoke()
}

class GetTaskListUseCaseImpl(
    private val taskRepository: TaskRepository
): GetTaskListUseCase {
    override suspend operator fun invoke() {
        TODO("Not yet implemented")
    }
}