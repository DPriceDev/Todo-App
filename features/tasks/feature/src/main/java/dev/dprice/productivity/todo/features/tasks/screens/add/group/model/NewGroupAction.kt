package dev.dprice.productivity.todo.features.tasks.screens.add.group.model

sealed class NewGroupAction {

    data class UpdateTitleText(val text: String) : NewGroupAction()

    data class UpdateTitleFocus(val focus: Boolean) : NewGroupAction()

    object SubmitForm : NewGroupAction()
}
