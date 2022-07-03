package dev.dprice.productivity.todo.features.tasks.screens.add.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Title
import androidx.compose.ui.text.input.ImeAction
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
    val details: EntryField = EntryField(
        hintText = "Details",
        icon = Icons.Default.Edit,
        maxLines = 5,
        maxLength = 256,
        imeAction = ImeAction.Done
    ),
    val buttonState: ButtonState = ButtonState.DISABLED
) : ContentForm {
    override val displayName: String = "New Group"

    override val isValid = title.isValid && details.isValid

    override val entries = listOf(
        FormEntry.Description(text = "Create a group TBC..."),
        FormEntry.Divider,
        FormEntry.Text(id = NewContentEntry.TITLE, entry = title),
        FormEntry.Text(id = NewContentEntry.DETAILS, entry = details),
        FormEntry.Divider,
        FormEntry.Button(id = NewContentEntry.SUBMIT, state = buttonState)
    )

    override fun withEnablement(enabled: Boolean) = copy(
        title = title.copy(enabled = enabled),
        details = details.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.DISABLED
    )
}
