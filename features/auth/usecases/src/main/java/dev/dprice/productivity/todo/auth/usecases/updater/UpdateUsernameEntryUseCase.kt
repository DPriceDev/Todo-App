package dev.dprice.productivity.todo.auth.usecases.updater

import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCase
import javax.inject.Inject

interface UpdateUsernameEntryUseCase {
    operator fun invoke(usernameEntry: EntryField, newUsername: String): EntryField

    operator fun invoke(usernameEntry: EntryField, newFocus: Boolean): EntryField

    operator fun invoke(
        usernameEntry: EntryField,
        newUsername: String,
        newFocus: Boolean
    ): EntryField
}

class UpdateUsernameEntryUseCaseImpl @Inject constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) : UpdateUsernameEntryUseCase {

    override fun invoke(usernameEntry: EntryField, newUsername: String): EntryField = this(
        usernameEntry,
        newUsername,
        usernameEntry.hasFocus
    )

    override fun invoke(usernameEntry: EntryField, newFocus: Boolean): EntryField = this(
        usernameEntry,
        usernameEntry.value,
        newFocus
    )

    override fun invoke(
        usernameEntry: EntryField,
        newUsername: String,
        newFocus: Boolean
    ) = updateEntryUseCase(
        usernameEntry,
        newUsername,
        newFocus,
        validator = usernameRegex::matches
    )

    companion object {
        private val usernameRegex = """[A-Z0-9a-z_-]{3,64}""".toRegex()
    }
}
