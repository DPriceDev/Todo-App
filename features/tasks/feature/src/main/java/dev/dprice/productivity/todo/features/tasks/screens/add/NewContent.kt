package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentState
import dev.dprice.productivity.todo.ui.components.Form
import dev.dprice.productivity.todo.ui.components.SlideSelector

@Composable
fun NewContent(
    state: NewContentState,
    onAction: (NewContentAction) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 16.dp)
            .animateContentSize()
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SlideSelector(
                state.forms.map { it.displayName },
                selected = state.forms.indexOf(state.currentForm)
            ) {
                onAction(NewContentAction.SelectContentType(it))
            }

            Form(
                entries = state.currentForm.entries,
                onAction = { onAction(NewContentAction.UpdateForm(it)) }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
