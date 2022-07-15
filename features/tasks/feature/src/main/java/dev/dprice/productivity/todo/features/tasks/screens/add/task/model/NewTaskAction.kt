package dev.dprice.productivity.todo.features.tasks.screens.add.task.model

sealed class NewTaskAction {
    data class UpdateTitleFocus(val newFocus: Boolean) : NewTaskAction()

    data class UpdateTitleText(val newText: String) : NewTaskAction()

    data class UpdateDetailsFocus(val newFocus: Boolean) : NewTaskAction()

    data class UpdateDetailsText(val newText: String) : NewTaskAction()

    object SubmitForm : NewTaskAction()


}
