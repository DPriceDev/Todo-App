package dev.dprice.productivity.todo.features.tasks.screens.add.group.model

import dev.dprice.productivity.todo.features.tasks.data.model.Colour
import dev.dprice.productivity.todo.features.tasks.data.model.Icon

sealed class NewGroupAction {

    data class UpdateTitleText(val text: String) : NewGroupAction()

    data class UpdateTitleFocus(val focus: Boolean) : NewGroupAction()

    object SubmitForm : NewGroupAction()

    data class SelectTab(val tab: NewGroupState.GroupTab?) : NewGroupAction()

    data class SelectColour(val colour: Colour) : NewGroupAction()

    data class SelectIcon(val icon: Icon) : NewGroupAction()
}
