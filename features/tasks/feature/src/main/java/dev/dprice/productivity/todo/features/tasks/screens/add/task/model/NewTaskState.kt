package dev.dprice.productivity.todo.features.tasks.screens.add.task.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Title
import androidx.compose.ui.text.input.ImeAction
import dev.dprice.productivity.todo.ui.components.buttons.ButtonState
import dev.dprice.productivity.todo.ui.components.text.EntryField

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
) {
    val isValid = title.isValid && details.isValid

    fun withEnablement(enabled: Boolean) = copy(
        title = title.copy(enabled = enabled),
        details = title.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.LOADING
    )
}
