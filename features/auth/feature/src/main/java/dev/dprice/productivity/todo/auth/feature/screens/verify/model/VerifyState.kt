package dev.dprice.productivity.todo.auth.feature.screens.verify.model

import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.ui.components.buttons.ButtonState
import dev.dprice.productivity.todo.ui.components.text.EntryField
import dev.dprice.productivity.todo.ui.transforms.DashedEntryVisualTransformation

data class VerifyState(
    val code: EntryField = EntryField(
        contentDescription = "Verification Code",
        errorText = "Please enter a 6 digit verification code",
        maxLength = 6,
        visualTransformation = DashedEntryVisualTransformation(6)
    ),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: ErrorState = ErrorState.None
)
