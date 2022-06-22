package dev.dprice.productivity.todo.features.tasks.screens.list.model

import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.ui.components.TitleBarState

data class TaskListState(
    val tasks: List<Task> = listOf(),
    val isLoading: Boolean = false,
    val selectedTaskId: String? = null,
    val titleBarState: TitleBarState = TitleBarState()
)
