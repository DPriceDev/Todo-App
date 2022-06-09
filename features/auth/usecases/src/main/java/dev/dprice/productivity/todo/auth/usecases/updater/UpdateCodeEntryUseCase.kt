package dev.dprice.productivity.todo.auth.usecases.updater

import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface UpdateCodeEntryUseCase {
    operator fun invoke(codeEntry: EntryField, newCode: String, ): EntryField

    operator fun invoke(codeEntry: EntryField, newFocus: Boolean): EntryField

    operator fun invoke(
        codeEntry: EntryField,
        newCode: String,
        newFocus: Boolean
    ): EntryField
}

class UpdateCodeEntryUseCaseImpl @Inject constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) : UpdateCodeEntryUseCase {

    override fun invoke(codeEntry: EntryField, newCode: String): EntryField = this(
        codeEntry,
        newCode,
        codeEntry.hasFocus
    )

    override fun invoke(codeEntry: EntryField, newFocus: Boolean): EntryField = this(
        codeEntry,
        codeEntry.value,
        newFocus
    )

    override operator fun invoke(
        codeEntry: EntryField,
        newCode: String,
        newFocus: Boolean
    ) = updateEntryUseCase(
        codeEntry,
        newCode,
        newFocus
    ) { code ->
        code.length == 6 && code.all { it.isDigit() }
    }
}
