package dev.dprice.productivity.todo.auth.signin.model

import dev.dprice.productivity.todo.ui.components.ButtonEnablement

sealed class ErrorState {
    object None: ErrorState()
    data class Message(val message: String) : ErrorState()
}

data class SignInState(
    val form: SignInForm = SignInForm(),
    val buttonEnablement: ButtonEnablement = ButtonEnablement.DISABLED,
    val error: ErrorState = ErrorState.None
)