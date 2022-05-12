package dev.dprice.productivity.todo.auth.usecases.updater

import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface UpdatePasswordEntryUseCase {
    operator fun invoke(
        passwordEntry: EntryField,
        newPassword: String,
        newFocus: Boolean
    ): EntryField
}

class UpdatePasswordEntryUseCaseImpl @Inject constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) : UpdatePasswordEntryUseCase {

    override fun invoke(
        passwordEntry: EntryField,
        newPassword: String,
        newFocus: Boolean
    ) = updateEntryUseCase(
        passwordEntry,
        newPassword,
        newFocus,
        passwordRegex::matches
    )

    companion object {
        private val passwordRegex = """^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@${'$'} %^&*-]).{8,}""".toRegex()
    }
}
