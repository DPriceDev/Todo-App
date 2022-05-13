package dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model

import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.ui.components.ButtonState

data class ResetPasswordState(
    val form: ResetPasswordForm = ResetPasswordForm(),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: ErrorState = ErrorState.None
)
