package dev.dprice.productivity.todo.auth.verify.viewmodel

import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface VerifyUserCodeUpdater {
    fun updateCode(code: EntryField, newCode: String, newFocus: Boolean) : EntryField
}

class VerifyUserCodeUpdaterImpl @Inject constructor() : VerifyUserCodeUpdater {

    override fun updateCode(entry: EntryField, newCode: String, newFocus: Boolean): EntryField {
        val shortenedCode = newCode.trim().take(entry.maxLength)
        val lostFocus = entry.hasFocus && !newFocus
        val isValid = shortenedCode.length == 6 && shortenedCode.all { it.isDigit() }
        val shouldValidate = entry.hasFocus && !newFocus && !isValid

        return entry.copy(
            value = shortenedCode,
            hasFocus = newFocus,
            isValid = isValid,
            shouldValidate = if(lostFocus) shouldValidate else entry.shouldValidate
        )
    }
}