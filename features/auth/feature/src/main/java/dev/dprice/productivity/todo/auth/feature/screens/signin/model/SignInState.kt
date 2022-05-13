package dev.dprice.productivity.todo.auth.feature.screens.signin.model

import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.ui.components.ButtonState

data class SignInState(
    val form: SignInForm = SignInForm(),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: ErrorState = ErrorState.None
)
