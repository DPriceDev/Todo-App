package dev.dprice.productivity.todo.features.groups.usecase

import dev.dprice.productivity.todo.features.groups.data.GroupRepository

class MarkGroupsAsDeletedUseCase(
   private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(ids: List<String>) {
        groupRepository.updateGroupsDeleted(true, ids)
    }
}