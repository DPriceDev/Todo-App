package dev.dprice.productivity.todo.auth.verify.model

import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.transformers.DashedEntryVisualTransformation

sealed class VerifyErrorState {
    object None : VerifyErrorState()
    data class Error(val message: String) : VerifyErrorState()
}

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
