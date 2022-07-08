package dev.dprice.productivity.todo.features.tasks.screens.add.group

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupAction
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupState
import dev.dprice.productivity.todo.ui.components.*

@Composable
fun NewGroupForm(
    form: NewGroupState,
    modifier: Modifier = Modifier,
    onAction: (NewGroupAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(
            text = "Create a new group for your tasks! Assign it a title and an icon and colour to keep track of it easier.",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        FormDivider(modifier = Modifier.padding(16.dp))

        RoundedEntryCard(
            entry = form.title,
            modifier = Modifier.onFocusChanged {
                onAction(NewGroupAction.UpdateTitleFocus(it.hasFocus))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = { onAction(NewGroupAction.UpdateTitleText(it)) }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            form.colour?.let { colour ->
                ColourPickerRow(
                    colour = colour,
                    modifier = Modifier.focusable(),
                    onClick = { /* todo */ }
                )
            }

            form.icon?.let {
                IconPickerRow(
                    icon = form.icon,
                    modifier = Modifier.focusable(),
                    onClick = { /* todo */ }
                )
            }
        }

        // Selector surfac

        FormDivider(modifier = Modifier.padding(16.dp))

        RoundedButton(
            text = "Create",
            buttonState = form.buttonState,
            modifier = Modifier.focusable(),
            onClick = { /* todo */ }
        )
    }
}