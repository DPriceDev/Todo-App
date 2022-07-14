package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.ui.components.text.EntryField
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCase

class UpdateTaskTitleEntryUseCase constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) {
    operator fun invoke(
        titleEntry: EntryField,
        newTitle: String? = null,
        newFocus: Boolean? = null
    ) = updateEntryUseCase(
        titleEntry,
        newTitle ?: titleEntry.value,
        newFocus ?: titleEntry.hasFocus,
        trimEnd = false,
        validator = titleRegex::matches
    )

    companion object {
        private val titleRegex = """.{1,64}""".toRegex()
    }
}