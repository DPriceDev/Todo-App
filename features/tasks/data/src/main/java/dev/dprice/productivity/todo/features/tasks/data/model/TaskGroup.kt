package dev.dprice.productivity.todo.features.tasks.data.model

data class TaskGroup(
    val group: Group?,
    val tasks: List<Task> = emptyList()
)
