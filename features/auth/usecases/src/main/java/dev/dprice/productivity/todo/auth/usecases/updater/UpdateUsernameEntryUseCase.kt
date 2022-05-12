package dev.dprice.productivity.todo.auth.usecases.updater

import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface UpdateUsernameEntryUseCase {
    operator fun invoke(
        usernameEntry: EntryField,
        newCode: String,
        newFocus: Boolean
    ): EntryField
}

class UpdateUsernameEntryUseCaseImpl @Inject constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) : UpdateUsernameEntryUseCase {

    override fun invoke(
        usernameEntry: EntryField,
        newCode: String,
        newFocus: Boolean
    ) = updateEntryUseCase(
        usernameEntry,
        newCode,
        newFocus,
        usernameRegex::matches
    )

    companion object {
        private val usernameRegex = """[A-Z0-9a-z_-]{3,64}""".toRegex()
    }
}
