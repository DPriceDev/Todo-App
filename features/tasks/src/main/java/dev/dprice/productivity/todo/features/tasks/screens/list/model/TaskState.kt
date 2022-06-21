package dev.dprice.productivity.todo.features.tasks.screens.list.model

import dev.dprice.productivity.todo.features.tasks.data.model.Task

data class TaskState(
    val task: Task,
    val isSelected: Boolean = false,
    val isSwiped: Boolean = false
)

fun Task.asTaskState(isSelected: Boolean = false): TaskState = TaskState(
    task = this,
    isSelected = isSelected
)

fun TaskState.asTask(): Task = task
