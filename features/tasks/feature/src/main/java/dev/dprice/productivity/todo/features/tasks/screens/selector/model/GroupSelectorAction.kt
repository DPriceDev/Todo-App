package dev.dprice.productivity.todo.features.tasks.screens.selector.model

sealed class GroupSelectorAction {

    data class SelectGroup(val id: String?) : GroupSelectorAction()

    data class LongPressGroup(val id: String?) : GroupSelectorAction()

    object DeleteGroups : GroupSelectorAction()

    object ExitEditMode : GroupSelectorAction()
}
