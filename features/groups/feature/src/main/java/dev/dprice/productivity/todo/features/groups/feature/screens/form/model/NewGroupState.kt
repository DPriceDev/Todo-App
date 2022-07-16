package dev.dprice.productivity.todo.features.groups.feature.screens.form.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import dev.dprice.productivity.todo.features.groups.data.model.Colour
import dev.dprice.productivity.todo.features.groups.data.model.Icon
import dev.dprice.productivity.todo.features.groups.feature.R
import dev.dprice.productivity.todo.ui.components.buttons.ButtonState
import dev.dprice.productivity.todo.ui.components.text.EntryField

data class NewGroupState(
    val title: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your group."
    ),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val colour: Colour = Colour.DEFAULT,
    val icon: Icon = Icon.NONE,
    val selectedTab: GroupTab? = null,
    val isDismissed: Boolean = false
) {
    val isValid = title.isValid

    fun withEnablement(enabled: Boolean) = copy(
        title = title.copy(enabled = enabled),
        buttonState = if (enabled) ButtonState.ENABLED else ButtonState.LOADING
    )

    enum class GroupTab(@StringRes val titleId: Int) {
        ICON(R.string.form_group_icon_title),
        COLOUR(R.string.form_group_colour_title)
    }
}
