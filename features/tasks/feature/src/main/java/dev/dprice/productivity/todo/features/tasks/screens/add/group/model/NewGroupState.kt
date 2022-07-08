package dev.dprice.productivity.todo.features.tasks.screens.add.group.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Title
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import dev.dprice.productivity.todo.features.tasks.screens.add.model.ContentForm.Type
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.components.FormEntry

data class NewGroupState(
    val title: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your group."
    ),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val colour: Color? = null,
    val icon: ImageVector? = null

) : ContentForm {
    override val displayName: String = "New Group"

    override val isValid = title.isValid

    override val entries = listOf(
        FormEntry.Description(
            text = "Create a new group for your tasks! Assign it a title and an icon and colour to keep track of it easier."
        ),
        FormEntry.Divider,
        FormEntry.Text(id = Type.TITLE, entry = title),
        FormEntry.Row(
            listOf(
                FormEntry.ColourPicker(id = Type.COLOUR, Color(0xFF000000)),
                FormEntry.IconPicker(id = Type.ICON, Icons.Default.Home),
            )
        ),
        FormEntry.Divider,
        FormEntry.Button(id = Type.SUBMIT, state = buttonState)
    )

    override fun withEnablement(enabled: Boolean) = copy(
        title = title.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.LOADING
    )
}
