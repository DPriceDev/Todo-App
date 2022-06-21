package dev.dprice.productivity.todo.auth.usecases.updater

import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCase
import javax.inject.Inject

interface UpdateEmailEntryUseCase {
    operator fun invoke(emailEntry: EntryField, newEmail: String): EntryField

    operator fun invoke(emailEntry: EntryField, newFocus: Boolean): EntryField

    operator fun invoke(
        emailEntry: EntryField,
        newEmail: String,
        newFocus: Boolean
    ): EntryField
}

class UpdateEmailEntryUseCaseImpl @Inject constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) : UpdateEmailEntryUseCase {

    override fun invoke(emailEntry: EntryField, newEmail: String): EntryField = this(
        emailEntry,
        newEmail,
        emailEntry.hasFocus
    )

    override fun invoke(emailEntry: EntryField, newFocus: Boolean): EntryField = this(
        emailEntry,
        emailEntry.value,
        newFocus
    )

    override operator fun invoke(
        emailEntry: EntryField,
        newEmail: String,
        newFocus: Boolean
    ) = updateEntryUseCase(
        emailEntry,
        newEmail,
        newFocus,
        validator = emailRegex::matches
    )

    companion object {
        private val emailRegex = """[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,8}""".toRegex()
    }
}
