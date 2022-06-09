package dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField

data class ForgotPasswordState(
    val username: EntryField = EntryField(
        icon = Icons.Outlined.Person,
        contentDescription = "Username",
        hintText = "Username",
        errorText = "Please enter a valid username."
    ),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: ErrorState = ErrorState.None
)
