package dev.dprice.productivity.todo.features.tasks.screens.add.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Title
import androidx.compose.ui.text.input.ImeAction
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.components.FormEntry

data class NewTaskForm(
    val titleEntry: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your task."
    ),
    val detailsEntry: EntryField = EntryField(
        hintText = "Details",
        icon = Icons.Default.Edit,
        maxLines = 5,
        maxLength = 512,
        imeAction = ImeAction.Done
    ),
    val buttonState: ButtonState = ButtonState.DISABLED
) : Form<NewTaskForm.Type> {
    override val isValid = titleEntry.isValid && detailsEntry.isValid

    override val entries = listOf(
        FormEntry.Description(
            text = "Create a new task! Enter a short todo for your task and an optional description if you want to add more detail."
        ),
        FormEntry.Divider,
        FormEntry.Text(id = Type.TITLE, entry = titleEntry),
        FormEntry.Text(id = Type.DETAILS, entry = detailsEntry),
        FormEntry.Divider,
        // todo: Type - boolean, check list, slider
        // Divider?
        // todo: Date time
        // todo: Repeatability
        // todo: reminders
        // Divider?
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
