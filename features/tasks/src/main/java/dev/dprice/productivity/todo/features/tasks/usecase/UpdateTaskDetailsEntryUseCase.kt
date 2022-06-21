package dev.dprice.productivity.todo.features.tasks.usecase

import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCase
import javax.inject.Inject

interface UpdateTaskDetailsEntryUseCase {
    operator fun invoke(detailsEntry: EntryField, newDetails: String): EntryField

    operator fun invoke(detailsEntry: EntryField, newFocus: Boolean): EntryField

    operator fun invoke(
        detailsEntry: EntryField,
        newDetails: String,
        newFocus: Boolean
    ): EntryField
}

class UpdateTaskDetailsEntryUseCaseImpl @Inject constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) : UpdateTaskDetailsEntryUseCase {

    override fun invoke(detailsEntry: EntryField, newDetails: String): EntryField = this(
        detailsEntry,
        newDetails,
        detailsEntry.hasFocus
    )

    override fun invoke(detailsEntry: EntryField, newFocus: Boolean): EntryField = this(
        detailsEntry,
        detailsEntry.value,
        newFocus
    )

    override fun invoke(
        detailsEntry: EntryField,
        newDetails: String,
        newFocus: Boolean
    ) = updateEntryUseCase(
        detailsEntry,
        newDetails,
        newFocus,
        trimEnd = false,
        validator = detailsRegex::matches
    )

    companion object {
        private val detailsRegex = """.{0,512}""".toRegex()
    }
}