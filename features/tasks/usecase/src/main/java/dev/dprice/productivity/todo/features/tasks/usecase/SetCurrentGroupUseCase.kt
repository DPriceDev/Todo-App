package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.GroupRepository

class SetCurrentGroupUseCase(private val groupRepository: GroupRepository) {

    suspend operator fun invoke(id: String?) {
        groupRepository.setCurrentGroup(id)
    }
}