package dev.dprice.productivity.todo.features.tasks.screens.add.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Title
import androidx.compose.ui.text.input.ImeAction
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.components.FormEntry

data class NewGroupForm(
    val titleEntry: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your group."
    ),
    val detailsEntry: EntryField = EntryField(
        hintText = "Details",
        icon = Icons.Default.Edit,
        maxLines = 5,
        maxLength = 256,
        imeAction = ImeAction.Done
    ),
    val buttonState: ButtonState = ButtonState.DISABLED
) : Form<NewGroupForm.Type> {
    override val isValid = titleEntry.isValid && detailsEntry.isValid

    override val entries = listOf(
        FormEntry.Description(text = "Create a group TBC..."),
        FormEntry.Divider,
        FormEntry.Text(id = Type.TITLE, entry = titleEntry),
        FormEntry.Text(id = Type.DETAILS, entry = detailsEntry),
        FormEntry.Divider,
        FormEntry.Button(id = Type.SUBMIT, state = buttonState)
    )

    override fun withEnablement(enabled: Boolean) = copy(
        titleEntry = titleEntry.copy(enabled = enabled),
        detailsEntry = titleEntry.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.DISABLED
    )

    enum class Type {
        TITLE,
        DETAILS,
        SUBMIT
    }
}
