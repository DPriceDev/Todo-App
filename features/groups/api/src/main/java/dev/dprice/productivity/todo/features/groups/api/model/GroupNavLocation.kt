package dev.dprice.productivity.todo.features.groups.api.model

sealed class GroupNavLocation(val route: String) {
    object Groups : GroupNavLocation("group")
    object GroupSelector : GroupNavLocation("group/selector")
    object NewGroup : GroupNavLocation("group/new")
}
