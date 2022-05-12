package dev.dprice.productivity.todo.auth.signin.ui.reset

import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface UpdatePasswordEntryUseCase {
    operator fun invoke(entryField: EntryField, newPassword: String, newFocus: Boolean): EntryField
}

class UpdatePasswordEntryUseCaseImpl @Inject constructor() : UpdatePasswordEntryUseCase {

    override fun invoke(entryField: EntryField, newPassword: String, newFocus: Boolean): EntryField {
        return entryField
    }
}
