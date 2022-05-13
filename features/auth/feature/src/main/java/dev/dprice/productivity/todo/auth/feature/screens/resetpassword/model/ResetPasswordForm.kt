package dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.transforms.DashedEntryVisualTransformation

data class ResetPasswordForm(
    val code: EntryField = EntryField(
        contentDescription = "Reset Code",
        errorText = "Please enter your 6 digit reset code",
        maxLength = 6,
        visualTransformation = DashedEntryVisualTransformation(6)
    ),
    val password: EntryField = EntryField(
        icon = Icons.Outlined.Password,
        contentDescription = "New Password",
        hintText = "New Password",
        errorText = "Please enter a valid password.",
        visualTransformation = PasswordVisualTransformation(),
        imeAction = ImeAction.Done
    ),
)