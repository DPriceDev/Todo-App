package dev.dprice.productivity.todo.features.tasks.screens.selector.model

import androidx.compose.runtime.Immutable
import dev.dprice.productivity.todo.features.tasks.data.model.Group

@Immutable
data class SelectorGroup(
    val group: Group?,
    val taskCount: Int
)

@Immutable
data class GroupSelectorState(
    val groups: List<SelectorGroup> = emptyList(),
    val isDismissed: Boolean = false
)
