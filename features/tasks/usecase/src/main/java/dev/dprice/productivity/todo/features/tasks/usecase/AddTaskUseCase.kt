package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
            id = UUID.randomUUID().toString(),
            groupId = "",
            title = title,
            details = details,
            isCompleted = false,
            finishDate = date,
            creationDate = Clock.System.now().toLocalDateTime(
                TimeZone.currentSystemDefault()
            )
        )

        taskRepository.addTask(newTask)
    }
}