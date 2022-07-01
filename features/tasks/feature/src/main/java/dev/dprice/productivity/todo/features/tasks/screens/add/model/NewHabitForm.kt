package dev.dprice.productivity.todo.features.tasks.screens.add.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import dev.dprice.productivity.todo.ui.components.EntryField

data class NewHabitForm(
    val titleEntry: EntryField = EntryField(
        hintText = "Title",
        icon = Icons.Default.Title,
        maxLength = 64,
        errorText = "Enter a title for your habit."
    ),
)
