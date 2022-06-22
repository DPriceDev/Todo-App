package dev.dprice.productivity.todo.features.tasks.dataimpl

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.dprice.productivity.todo.features.tasks.TaskItem
import dev.dprice.productivity.todo.features.tasks.TasksQueries
import dev.dprice.productivity.todo.features.tasks.data.TaskRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl constructor(
    private val taskQueries: TasksQueries
) : TaskRepository {

    override suspend fun addTask(task: Task) {
        taskQueries.insert(
            task.id,
            task.title,
            task.description,
            task.isComplete,
            task.dateTime
        )
        // todo: add to server
    }

    override suspend fun updateTask(task: Task) {
        taskQueries.insert(
            task.id,
            task.title,
            task.description,
            task.isComplete,
            task.dateTime
        )
        // todo: update on server
    }

    override suspend fun deleteTask(id: String) {
        taskQueries.delete(id)
        // todo: Delete from server
    }

    override fun getTasks(): Flow<List<Task>> {
        // todo: Fetch from server
        return taskQueries.selectAll()
            .asFlow()
            .mapToList()
            .map { taskList ->
                taskList.map { it.toTask() }
            }
    }
}

fun TaskItem.toTask() : Task {
    return Task(
        id,
        title,
        details,
        isComplete,
        date
    )
}