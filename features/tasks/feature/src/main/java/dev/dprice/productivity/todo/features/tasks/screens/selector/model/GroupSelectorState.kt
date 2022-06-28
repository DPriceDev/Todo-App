package dev.dprice.productivity.todo.features.tasks.screens.selector.model

import dev.dprice.productivity.todo.features.tasks.data.model.Group

data class SelectorGroup(
    val group: Group?,
    val taskCount: Int
)

data class GroupSelectorState(
    val groups: List<SelectorGroup> = emptyList()
)
