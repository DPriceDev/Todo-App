package dev.dprice.productivity.todo.features.tasks.screens.add.group.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField

data class NewGroupState(
    val title: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your group."
    ),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val colour: Color? = null,
    val colours: List<Color> = emptyList(),
    val icon: ImageVector? = null,
    val icons: List<ImageVector> = emptyList()
) {
    val isValid = title.isValid

    fun withEnablement(enabled: Boolean) = copy(
        title = title.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.LOADING
    )
}
