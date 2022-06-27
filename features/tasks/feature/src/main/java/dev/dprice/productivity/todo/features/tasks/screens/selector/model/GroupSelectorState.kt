package dev.dprice.productivity.todo.features.tasks.screens.selector.model

import dev.dprice.productivity.todo.features.tasks.data.model.Group

data class GroupSelectorState(
    val groups: List<Group> = emptyList()
)
