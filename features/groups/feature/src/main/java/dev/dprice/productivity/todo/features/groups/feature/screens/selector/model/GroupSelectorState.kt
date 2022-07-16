package dev.dprice.productivity.todo.features.groups.feature.screens.selector.model

import androidx.compose.runtime.Immutable
import dev.dprice.productivity.todo.features.groups.data.model.Group
import kotlinx.coroutines.flow.SharedFlow

@Immutable
data class SelectorGroup(
    val group: Group?,
    val taskCount: Int,
    val isSelected: Boolean = false
)

@Immutable
data class GroupSelectorState(
    val groups: List<SelectorGroup> = emptyList(),
    val isEditMode: Boolean = false,
    val isDismissed: Boolean = false,
    val messageFlow: SharedFlow<String>
)
