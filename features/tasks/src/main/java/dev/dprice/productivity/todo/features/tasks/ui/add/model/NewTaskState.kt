package dev.dprice.productivity.todo.features.tasks.ui.add.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Title
import dev.dprice.productivity.todo.ui.components.EntryField

data class NewTaskState(
    val titleEntry: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title
    ),
    val descriptionEntry: EntryField = EntryField(
        hintText = "Details",
        icon = Icons.Default.Edit
    ),
)