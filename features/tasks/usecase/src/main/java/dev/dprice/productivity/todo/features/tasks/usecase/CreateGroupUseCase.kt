package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.features.tasks.data.GroupRepository
import dev.dprice.productivity.todo.features.tasks.data.model.Colour
import dev.dprice.productivity.todo.features.tasks.data.model.Group
import dev.dprice.productivity.todo.features.tasks.data.model.Icon
import java.util.*

class CreateGroupUseCase(
    val groupRepository: GroupRepository
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