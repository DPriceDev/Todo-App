package dev.dprice.productivity.todo.features.groups.usecase

import dev.dprice.productivity.todo.features.groups.data.GroupRepository
import dev.dprice.productivity.todo.features.groups.data.model.Colour
import dev.dprice.productivity.todo.features.groups.data.model.Group
import dev.dprice.productivity.todo.features.groups.data.model.Icon
import java.util.*

class CreateGroupUseCase(
    private val groupRepository: GroupRepository
) {

    suspend operator fun invoke(
        title: String,
        colour: Colour,
        icon: Icon
    ) {
        groupRepository.addGroup(
            Group(
                id = UUID.randomUUID().toString(),
                name = title,
                colour = colour,
                icon = icon
            )
        )
    }
}