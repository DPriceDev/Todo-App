package dev.dprice.productivity.todo.auth.verify.model

import dev.dprice.productivity.todo.auth.verify.ui.DashedEntryVisualTransformation
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField

data class VerifyState(
    val code: EntryField = EntryField(
        contentDescription = "Verification Code",
        errorText = "Please enter a 6 digit verification code",
        maxLength = 6,
        visualTransformation = DashedEntryVisualTransformation(6)
    ),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: VerifyErrorState = VerifyErrorState.None
)
