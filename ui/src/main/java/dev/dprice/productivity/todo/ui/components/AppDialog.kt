package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun AppDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    confirmText: String = "Confirm",
    dismissText: String = "Dismiss",
    onDismiss: () -> Unit = { },
    isDialogShown: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    AlertDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
        },
        modifier = modifier,
        onDismissRequest = {
            onDismiss()
            isDialogShown.value = false
        },
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(8.dp),
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                RoundedButton(
                    text = dismissText,
                    contentPadding = PaddingValues(
                        vertical = 16.dp,
                        horizontal = 40.dp
                    ),
                    onClick = {
                        onDismiss()
                        isDialogShown.value = false
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                RoundedButton(
                    text = confirmText,
                    contentPadding = PaddingValues(
                        vertical = 16.dp,
                        horizontal = 40.dp
                    ),
                    onClick = {
                        onConfirm()
                        isDialogShown.value = false
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewAppDialog() {
    TodoAppTheme() {
        AppDialog(
            title = "Example title",
            message = "Example Message",
            onConfirm = { /*TODO*/ }
        )
    }
}