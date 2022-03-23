package dev.dprice.productivity.todo.auth.signup.viewmodel

import dev.dprice.productivity.todo.auth.signup.model.SignUpAction
import dev.dprice.productivity.todo.auth.signup.model.SignUpForm
import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface SignUpFormUpdater {
    fun updateEntry(form: SignUpForm, action: SignUpAction) : SignUpForm
}

class SignUpFormUpdaterImpl @Inject constructor() : SignUpFormUpdater {

    override fun updateEntry(form: SignUpForm, action: SignUpAction): SignUpForm {
        return when (action.type) {
            SignUpAction.Type.UPDATE_EMAIL -> form.copy(
                email = updateEntry(form.email, action.value, action.focus) {
                    validate(it, emailRegex)
                }
            )
            SignUpAction.Type.UPDATE_USERNAME -> form.copy(
                username = updateEntry(form.username, action.value, action.focus) {
                    validate(it, usernameRegex)
                }
            )
            SignUpAction.Type.UPDATE_PASSWORD -> form.copy(
                password = updateEntry(form.password, action.value, action.focus) {
                    validate(it, passwordRegex)
                }
            )
        }
    }

    private fun validate(value: String, regex: Regex) : Boolean = value.matches(regex)

    private fun updateEntry(
        entry: EntryField,
        newValue: String,
        newFocus: Boolean,
        validator: (String) -> Boolean
    ): EntryField {
        val shortenedValue = newValue.take(entry.maxLength)
        val lostFocus = entry.hasFocus && !newFocus
        val isValid = validator(shortenedValue)
        val shouldValidate = entry.hasFocus && !newFocus && !isValid

        return entry.copy(
            value = shortenedValue,
            hasFocus = newFocus,
            isValid = isValid,
            shouldValidate = if(lostFocus) shouldValidate else entry.shouldValidate
        )
    }

    companion object {
        private val emailRegex = """[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,8}""".toRegex()
        private val usernameRegex = """[A-Z0-9a-z_-]{3,64}""".toRegex()
        private val passwordRegex = """^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@${'$'} %^&*-]).{8,}""".toRegex()
    }
}