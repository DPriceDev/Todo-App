package dev.dprice.productivity.todo.auth.feature.usecase

import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.usecase.UpdateEntryUseCase
import javax.inject.Inject

interface UpdatePasswordEntryUseCase {
    operator fun invoke(passwordEntry: EntryField, newPassword: String): EntryField

    operator fun invoke(passwordEntry: EntryField, newFocus: Boolean): EntryField

    operator fun invoke(
        passwordEntry: EntryField,
        newPassword: String,
        newFocus: Boolean
    ): EntryField
}

class UpdatePasswordEntryUseCaseImpl @Inject constructor(
    private val updateEntryUseCase: UpdateEntryUseCase
) : UpdatePasswordEntryUseCase {

    override fun invoke(passwordEntry: EntryField, newFocus: Boolean): EntryField = this(
        passwordEntry,
        passwordEntry.value,
        newFocus
    )

    override fun invoke(passwordEntry: EntryField, newPassword: String): EntryField = this(
        passwordEntry,
        newPassword,
        passwordEntry.hasFocus
    )

    override fun invoke(
        passwordEntry: EntryField,
        newPassword: String,
        newFocus: Boolean
    ) = updateEntryUseCase(
        passwordEntry,
        newPassword,
        newFocus,
        validator = passwordRegex::matches
    )

    companion object {
        private val passwordRegex = """^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@${'$'} %^&*-]).{8,}""".toRegex()
    }
}
