package dev.dprice.productivity.todo.features.tasks.screens.add.task

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.R
import dev.dprice.productivity.todo.features.tasks.screens.add.task.model.NewTaskAction
import dev.dprice.productivity.todo.features.tasks.screens.add.task.model.NewTaskState
import dev.dprice.productivity.todo.ui.components.FormDivider
import dev.dprice.productivity.todo.ui.components.buttons.RoundedButton
import dev.dprice.productivity.todo.ui.components.text.RoundedEntryCard

@Composable
fun NewTaskForm(
    state: NewTaskState,
    modifier: Modifier = Modifier,
    onAction: (NewTaskAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.then(modifier)
    ) {
        Text(
            text = stringResource(id = R.string.task_form_description),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        FormDivider(modifier = Modifier.padding(20.dp))

        RoundedEntryCard(
            entry = state.title,
            modifier = Modifier.onFocusChanged {
                onAction(NewTaskAction.UpdateTitleFocus(it.hasFocus))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = { onAction(NewTaskAction.UpdateTitleText(it)) }
        )

        RoundedEntryCard(
            entry = state.details,
            modifier = Modifier.onFocusChanged {
                onAction(NewTaskAction.UpdateDetailsFocus(it.hasFocus))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = { onAction(NewTaskAction.UpdateDetailsText(it)) }
        )

        FormDivider(modifier = Modifier.padding(20.dp))

        // todo: group selector

        // todo: reminder selector

        FormDivider(modifier = Modifier.padding(20.dp))

        RoundedButton(
            text = stringResource(id = R.string.form_create_button),
            buttonState = state.buttonState,
            modifier = Modifier.focusable(),
            onClick = { onAction(NewTaskAction.SubmitForm) }
        )
    }
}