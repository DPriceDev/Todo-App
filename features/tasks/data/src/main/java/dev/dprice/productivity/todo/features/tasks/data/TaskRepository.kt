package dev.dprice.productivity.todo.features.tasks.data

import dev.dprice.productivity.todo.features.tasks.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(id: String)

    fun getTasks() : Flow<List<Task>>
}
