package dev.dprice.productivity.todo.features.groups.usecase

import dev.dprice.productivity.todo.features.groups.data.GroupRepository
import dev.dprice.productivity.todo.features.groups.data.model.Group
import kotlinx.coroutines.flow.Flow

class GetCurrentGroupUseCase(private val groupRepository: GroupRepository) {

    operator fun invoke(): Flow<Group?> = groupRepository.getCurrentGroup()
}