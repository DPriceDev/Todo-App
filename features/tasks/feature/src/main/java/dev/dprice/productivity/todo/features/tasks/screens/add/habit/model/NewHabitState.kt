package dev.dprice.productivity.todo.features.tasks.screens.add.habit.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import dev.dprice.productivity.todo.ui.components.buttons.ButtonState
import dev.dprice.productivity.todo.ui.components.text.EntryField

data class NewHabitState(
    val title: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your habit."
    ),
    val buttonState: ButtonState = ButtonState.DISABLED
) {
    val isValid = title.isValid

    fun withEnablement(enabled: Boolean) = copy(
        title = title.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.LOADING
    )
}
