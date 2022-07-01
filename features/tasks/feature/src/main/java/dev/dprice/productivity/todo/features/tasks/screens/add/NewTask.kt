package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction.NewTaskAction
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewTaskForm
import dev.dprice.productivity.todo.ui.components.RoundedEntryCard
import dev.dprice.productivity.todo.ui.theme.Yellow

@Composable
fun NewTask(
    form: NewTaskForm,
    onAction: (NewTaskAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // todo: group, existing or new

        Text(
            text = "Create a new task! Enter a short todo for your task and an optional description if you want to add more detail.",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Divider(
            color = Yellow,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val focusManager = LocalFocusManager.current

        RoundedEntryCard(
            entry = form.titleEntry,
            modifier = Modifier.onFocusChanged {
                onAction(NewTaskAction.UpdateTitleFocus(it.hasFocus))
            },
            onTextChanged = { onAction(NewTaskAction.UpdateTitleValue(it)) },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
        )

        RoundedEntryCard(
            entry = form.detailsEntry,
            modifier = Modifier.onFocusChanged {
                onAction(NewTaskAction.UpdateDescriptionFocus(it.hasFocus))
            },
            onTextChanged = { onAction(NewTaskAction.UpdateDescriptionValue(it)) },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
        )

        // Divider?

        // todo: Type - boolean, check list, slider

        // Divider?

        // todo: Date time

        // todo: Repeatability

        // todo: reminders
    }
}