package dev.dprice.productivity.todo.auth.feature.screens.signin.model

sealed class SignInEvent {
    data class UpdateUsernameValue(val value: String) : SignInEvent()
    data class UpdateUsernameFocus(val focus: Boolean) : SignInEvent()
    data class UpdatePasswordValue(val value: String) : SignInEvent()
    data class UpdatePasswordFocus(val focus: Boolean) : SignInEvent()
}
