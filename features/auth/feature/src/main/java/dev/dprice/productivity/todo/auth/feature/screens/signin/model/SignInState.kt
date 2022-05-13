package dev.dprice.productivity.todo.auth.feature.screens.signin.model

import androidx.annotation.StringRes
import dev.dprice.productivity.todo.ui.components.ButtonState

sealed class ErrorState {
    object None : ErrorState()
    data class Message(@StringRes val messageId: Int) : ErrorState()
}

data class SignInState(
    val form: SignInForm = SignInForm(),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val error: ErrorState = ErrorState.None
)
