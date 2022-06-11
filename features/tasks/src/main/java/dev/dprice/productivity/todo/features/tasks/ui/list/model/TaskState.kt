package dev.dprice.productivity.todo.features.tasks.ui.list.model

import dev.dprice.productivity.todo.features.tasks.data.model.Task

data class TaskState(
    val task: Task,
    val isSelected: Boolean = false
)

fun Task.asTaskState(isSelected: Boolean = false): TaskState = TaskState(
    task = this,
    isSelected = isSelected
)

fun TaskState.asTask(): Task = task
