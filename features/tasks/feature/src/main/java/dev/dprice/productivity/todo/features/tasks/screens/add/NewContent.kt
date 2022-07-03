package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentState
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentType
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
                state.contentTypes.map { it.displayName },
                selected = state.selectedContentType.ordinal
            ) {
                onAction(NewContentAction.SelectContentType(it))
            }

            when(state.selectedContentType) {
                NewContentType.TASK -> Form(
                    entries = state.taskForm.entries,
                    onAction = { onAction(NewContentAction.UpdateTaskForm(it)) }
                )
                NewContentType.HABIT -> Form(
                    entries = state.habitForm.entries,
                    onAction = { onAction(NewContentAction.UpdateHabitForm(it)) }
                )
                NewContentType.GROUP -> Form(
                    entries = state.groupForm.entries,
                    onAction = { onAction(NewContentAction.UpdateGroupForm(it)) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
