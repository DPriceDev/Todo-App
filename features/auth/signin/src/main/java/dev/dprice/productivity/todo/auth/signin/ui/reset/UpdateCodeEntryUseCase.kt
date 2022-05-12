package dev.dprice.productivity.todo.auth.signin.ui.reset

import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface UpdateCodeEntryUseCase {
    operator fun invoke(entryField: EntryField, newCode: String, newFocus: Boolean): EntryField
}

class UpdateCodeEntryUseCaseImpl @Inject constructor() : UpdateCodeEntryUseCase {

    override fun invoke(entryField: EntryField, newCode: String, newFocus: Boolean): EntryField {
        return entryField
    }
}
