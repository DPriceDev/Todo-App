package dev.dprice.productivity.todo.features.tasks.ui.list.model

sealed class TaskListAction {
    data class UpdateTasks(val tasks: List<TaskState>) : TaskListAction()
    data class SelectTask(val task: TaskState) : TaskListAction()
    data class CompleteTask(val task: TaskState) : TaskListAction()
    data class UnCompleteTask(val task: TaskState) : TaskListAction()
    data class DeleteTask(val task: TaskState) : TaskListAction()
    data class AddTask(val task: TaskState) : TaskListAction()
}
