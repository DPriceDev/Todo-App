package dev.dprice.productivity.todo.features.tasks.screens.add.model

sealed class NewTaskAction {
    object CreateTask : NewTaskAction()

    data class UpdateTitleValue(val value: String) : NewTaskAction()
    data class UpdateTitleFocus(val focus: Boolean) : NewTaskAction()

    data class UpdateDescriptionValue(val value: String) : NewTaskAction()
    data class UpdateDescriptionFocus(val focus: Boolean) : NewTaskAction()
}
