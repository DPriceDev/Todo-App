package dev.dprice.productivity.todo.features.tasks.screens.add.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import dev.dprice.productivity.todo.features.tasks.screens.add.model.ContentForm.Type
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.components.FormEntry

data class NewGroupForm(
    val title: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your group."
    ),
    val buttonState: ButtonState = ButtonState.DISABLED
) : ContentForm {
    override val displayName: String = "New Group"

    override val isValid = title.isValid

    override val entries = listOf(
        FormEntry.Description(text = "Create a group TBC..."),
        FormEntry.Divider,
        FormEntry.Text(id = Type.TITLE, entry = title),
        // todo: Colour,
        // todo: Icon,
        FormEntry.Divider,
        FormEntry.Button(id = Type.SUBMIT, state = buttonState)
    )

    override fun withEnablement(enabled: Boolean) = copy(
        title = title.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.LOADING
    )
}
