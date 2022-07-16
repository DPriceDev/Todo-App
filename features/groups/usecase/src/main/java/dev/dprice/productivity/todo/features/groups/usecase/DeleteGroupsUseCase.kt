package dev.dprice.productivity.todo.features.groups.usecase

import dev.dprice.productivity.todo.features.groups.data.GroupRepository

class DeleteGroupsUseCase(
    private val groupRepository: GroupRepository
) {

    suspend operator fun invoke(ids: List<String>) {
        groupRepository.deleteGroups(ids)
    }
}