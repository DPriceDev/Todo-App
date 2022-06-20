package dev.dprice.productivity.todo.features.tasks.ui.list.model

sealed class TaskListAction {
    data class UpdateTasks(val tasks: List<TaskState>) : TaskListAction()
    data class SelectTask(val task: TaskState) : TaskListAction()
    data class CompleteTask(val task: TaskState) : TaskListAction()
    data class SwipeTask(val task: TaskState) : TaskListAction()
    data class DeleteTask(val task: TaskState) : TaskListAction()
    data class AddTask(val task: TaskState) : TaskListAction()

    object SearchButtonClicked : TaskListAction()

    data class UpdateSearchText(val value: String) : TaskListAction()
    data class UpdateSearchFocus(val focus: Boolean) : TaskListAction()
}
