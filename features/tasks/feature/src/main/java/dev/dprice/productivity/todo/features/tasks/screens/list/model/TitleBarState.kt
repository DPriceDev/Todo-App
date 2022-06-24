package dev.dprice.productivity.todo.features.tasks.screens.list.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import dev.dprice.productivity.todo.ui.components.EntryField

data class TitleBarState(
    val searchEntry: EntryField = EntryField(
        value = "",
        hintText = "Search Tasks",
        icon = Icons.Outlined.Search
    ),
    val isSearchShown: Boolean = false,
    val filter: TaskFilter = TaskFilter.ALL
)