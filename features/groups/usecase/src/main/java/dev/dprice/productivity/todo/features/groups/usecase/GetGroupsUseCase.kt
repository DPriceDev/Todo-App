package dev.dprice.productivity.todo.features.groups.usecase

import dev.dprice.productivity.todo.features.groups.data.GroupRepository
import kotlinx.coroutines.flow.map

class GetGroupsUseCase(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(includeDeleted: Boolean = false) = groupRepository
        .getGroups()
        .map { groups ->
            groups.filter { if (includeDeleted) true else !it.isDeleted }
        }
}