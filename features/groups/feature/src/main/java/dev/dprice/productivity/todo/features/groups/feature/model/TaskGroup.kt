package dev.dprice.productivity.todo.features.groups.feature.model

import dev.dprice.productivity.todo.features.groups.data.model.Group
import dev.dprice.productivity.todo.features.tasks.data.model.Task

data class TaskGroup(
    val group: Group?,
    val tasks: List<Task> = emptyList()
)
