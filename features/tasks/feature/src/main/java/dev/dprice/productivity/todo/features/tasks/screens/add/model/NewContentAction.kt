package dev.dprice.productivity.todo.features.tasks.screens.add.model

sealed interface NewContentAction {

    object SubmitClicked: NewContentAction

    data class SelectContentType(val index: Int) : NewContentAction

    sealed class NewTaskAction : NewContentAction {
        data class UpdateTitleValue(val value: String) : NewTaskAction()
        data class UpdateTitleFocus(val focus: Boolean) : NewTaskAction()

        data class UpdateDescriptionValue(val value: String) : NewTaskAction()
        data class UpdateDescriptionFocus(val focus: Boolean) : NewTaskAction()
    }

    sealed class NewGroupAction : NewContentAction {
        data class UpdateTitleValue(val value: String) : NewGroupAction()
        data class UpdateTitleFocus(val focus: Boolean) : NewGroupAction()

        data class UpdateDescriptionValue(val value: String) : NewGroupAction()
        data class UpdateDescriptionFocus(val focus: Boolean) : NewGroupAction()
    }
}

