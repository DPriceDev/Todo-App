package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCase
import javax.inject.Inject

interface UpdateTaskTitleEntryUseCase {
    operator fun invoke(titleEntry: EntryField, newTitle: String): EntryField

    operator fun invoke(titleEntry: EntryField, newFocus: Boolean): EntryField

    operator fun invoke(
        titleEntry: EntryField,
        newTitle: String,
        newFocus: Boolean
    ): EntryField
}

class UpdateTaskTitleEntryUseCaseImpl @Inject constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) : UpdateTaskTitleEntryUseCase {

    override fun invoke(titleEntry: EntryField, newTitle: String): EntryField = this(
        titleEntry,
        newTitle,
        titleEntry.hasFocus
    )

    override fun invoke(titleEntry: EntryField, newFocus: Boolean): EntryField = this(
        titleEntry,
        titleEntry.value,
        newFocus
    )

    override fun invoke(
        titleEntry: EntryField,
        newTitle: String,
        newFocus: Boolean
    ) = updateEntryUseCase(
        titleEntry,
        newTitle,
        newFocus,
        trimEnd = false,
        validator = titleRegex::matches
    )

    companion object {
        private val titleRegex = """.{1,64}""".toRegex()
    }
}