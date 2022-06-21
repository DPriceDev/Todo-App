package dev.dprice.productivity.todo.features.tasks.screens.list.model

import dev.dprice.productivity.todo.ui.components.TitleBarState

data class TaskListState(
    val tasks: List<TaskState> = listOf(),
    val isLoading: Boolean = false,
    val titleBarState: TitleBarState = TitleBarState()
)
