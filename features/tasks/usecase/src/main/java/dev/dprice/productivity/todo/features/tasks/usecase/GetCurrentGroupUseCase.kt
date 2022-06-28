package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.GroupRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Group
import kotlinx.coroutines.flow.Flow

class GetCurrentGroupUseCase(private val groupRepository: GroupRepository) {

    operator fun invoke(): Flow<Group?> = groupRepository.getCurrentGroup()
}