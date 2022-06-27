package dev.dprice.productivity.todo.features.tasks.screens.list.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import dev.dprice.productivity.todo.features.tasks.data.model.Group
import dev.dprice.productivity.todo.ui.components.EntryField

data class TitleBarState(
    val searchEntry: EntryField = EntryField(
        value = "",
        hintText = "Search Tasks",
        icon = Icons.Outlined.Search
    ),
    val isSearchShown: Boolean = false,
    val groups: List<Group> = emptyList(),
    val currentGroup: Group? = null,
    val currentDateRange: DateFilter = DateFilter.ALL,
    val filter: TaskFilter = TaskFilter.ALL,
)