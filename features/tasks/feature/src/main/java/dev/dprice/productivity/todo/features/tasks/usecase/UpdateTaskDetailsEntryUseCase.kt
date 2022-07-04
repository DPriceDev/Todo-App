package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCase

class UpdateTaskDetailsEntryUseCase constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) {
    operator fun invoke(
        entry: EntryField,
        newDetails: String? = null,
        newFocus: Boolean? = null,
    ) = updateEntryUseCase(
        entry,
        newValue = newDetails ?: entry.value,
        newFocus = newFocus ?: entry.hasFocus,
        trimEnd = false,
        validator = detailsRegex::matches
    )

    companion object {
        private val detailsRegex = """.{0,512}""".toRegex()
    }
}