package dev.dprice.productivity.todo.features.groups.usecase

import dev.dprice.productivity.todo.features.groups.data.GroupRepository

class SetCurrentGroupUseCase(private val groupRepository: GroupRepository) {

    suspend operator fun invoke(id: String?) {
        groupRepository.setCurrentGroup(id)
    }
}