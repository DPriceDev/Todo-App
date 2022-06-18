package dev.dprice.productivity.todo.features.tasks.ui.list.model

data class TaskListState(
    val tasks: List<TaskState> = listOf(),
    val isLoading: Boolean = false,
    val titleBarState: TitleBarState = TitleBarState()
)
