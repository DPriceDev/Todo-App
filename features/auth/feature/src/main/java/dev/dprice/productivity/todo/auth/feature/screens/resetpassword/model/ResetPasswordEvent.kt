package dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model

sealed class ResetPasswordEvent {
    data class UpdatePasswordValue(val value: String) : ResetPasswordEvent()
    data class UpdatePasswordFocus(val focus: Boolean) : ResetPasswordEvent()
    data class UpdateCodeValue(val value: String) : ResetPasswordEvent()
    data class UpdateCodeFocus(val focus: Boolean) : ResetPasswordEvent()
}