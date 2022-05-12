package dev.dprice.productivity.todo.auth.signup.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import dev.dprice.productivity.todo.ui.components.EntryField

data class SignUpForm(
    val email: EntryField = EntryField(
        icon = Icons.Outlined.Email,
        contentDescription = "Email",
        hintText = "Email",
        errorText = "Please enter a valid email."
    ),
    val username: EntryField = EntryField(
        icon = Icons.Outlined.Person,
        contentDescription = "Username",
        hintText = "Username",
        errorText = "Please enter a valid username."
    ),
    val password: EntryField = EntryField(
        icon = Icons.Outlined.Password,
        contentDescription = "Password",
        hintText = "Password",
        errorText = "Please enter a valid password.",
        visualTransformation = PasswordVisualTransformation(),
        imeAction = ImeAction.Done
    )
)
