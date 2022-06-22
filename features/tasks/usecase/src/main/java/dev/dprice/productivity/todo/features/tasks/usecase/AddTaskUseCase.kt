package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import kotlinx.datetime.LocalDateTime
import java.util.*

interface AddTaskUseCase {
    suspend operator fun invoke(
        title: String,
        details: String,
        date: LocalDateTime
    )
}

class AddTaskUseCaseImpl(private val taskRepository: TaskRepository) : AddTaskUseCase {

    override suspend fun invoke(
        title: String,
        details: String,
        date: LocalDateTime
    ) {
        val newTask = Task(
            UUID.randomUUID().toString(),
            title,
            details,
            isComplete = false,
            date
        )

        taskRepository.addTask(newTask)
    }
}