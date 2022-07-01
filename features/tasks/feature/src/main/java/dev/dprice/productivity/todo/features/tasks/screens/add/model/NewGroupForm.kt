package dev.dprice.productivity.todo.features.tasks.screens.add.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Title
import androidx.compose.ui.text.input.ImeAction
import dev.dprice.productivity.todo.ui.components.EntryField

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
)
