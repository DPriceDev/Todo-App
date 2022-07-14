package dev.dprice.productivity.todo.auth.feature.components.signout.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.buttons.ButtonState
import dev.dprice.productivity.todo.ui.components.buttons.RoundedButton

@Composable
fun SignOutButton(
    onSignOut: () -> Unit,
    buttonState: ButtonState
) {
    RoundedButton(
        text = "Log out",
        onClick = onSignOut,
        modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
        contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
        buttonState = buttonState,
    )
}
