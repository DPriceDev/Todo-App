package dev.dprice.productivity.todo.features.tasks.screens.add.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.components.FormEntry

data class NewHabitForm(
    val titleEntry: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your habit."
    ),
    val buttonState: ButtonState = ButtonState.DISABLED
): Form<NewHabitForm.Type> {

    override val isValid = titleEntry.isValid

    override val entries = listOf(
        FormEntry.Description(text = "Enter Habit TBC..."),
        FormEntry.Divider,
        FormEntry.Text(id = Type.TITLE, entry = titleEntry),
        FormEntry.Divider,
        FormEntry.Button(id = Type.SUBMIT, state = buttonState)
    )

    override fun withEnablement(enabled: Boolean) = copy(
        titleEntry = titleEntry.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.DISABLED
    )

    enum class Type {
        TITLE,
        SUBMIT
    }
}
