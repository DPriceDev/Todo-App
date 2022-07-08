package dev.dprice.productivity.todo.features.tasks.screens.add.task.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Title
import androidx.compose.ui.text.input.ImeAction
import dev.dprice.productivity.todo.features.tasks.screens.add.model.ContentForm.Type
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.components.FormEntry

data class NewTaskState(
    val title: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your task."
    ),
    val details: EntryField = EntryField(
        hintText = "Details",
        icon = Icons.Default.Edit,
        maxLines = 5,
        maxLength = 512,
        imeAction = ImeAction.Done
    ),
    val buttonState: ButtonState = ButtonState.DISABLED
) : ContentForm {
    override val displayName: String = "New Task"

    override val isValid = title.isValid && details.isValid

    override val entries = listOf(
        FormEntry.Description(
            text = "Create a new task! Enter a short todo for your task and an optional description if you want to add more detail."
        ),
        FormEntry.Divider,
        FormEntry.Text(id = Type.TITLE, entry = title),
        FormEntry.Text(id = Type.DETAILS, entry = details),
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
        title = title.copy(enabled = enabled),
        details = title.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.LOADING
    )
}
