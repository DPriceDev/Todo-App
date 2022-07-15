package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.GroupRepository

class DeleteGroupsUseCase(
    private val groupRepository: GroupRepository
) {

    suspend operator fun invoke(ids: List<String>) {
        groupRepository.deleteGroups(ids)
    }
}