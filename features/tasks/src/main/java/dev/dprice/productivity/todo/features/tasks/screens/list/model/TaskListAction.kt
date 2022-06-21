package dev.dprice.productivity.todo.features.tasks.screens.list.model

sealed class TaskListAction {
    data class UpdateTasks(val tasks: List<TaskState>) : TaskListAction()
    data class SelectTask(val id: String) : TaskListAction()
    data class CompleteTask(val id: String) : TaskListAction()
    data class SwipeTask(val id: String) : TaskListAction()
    data class DeleteTask(val id: String) : TaskListAction()

    object SearchButtonClicked : TaskListAction()

    data class UpdateSearchText(val value: String) : TaskListAction()
    data class UpdateSearchFocus(val focus: Boolean) : TaskListAction()
}
