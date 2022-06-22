package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.NewTask
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewTaskAction
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewTaskState
import dev.dprice.productivity.todo.ui.components.SlideSelector

@Composable
fun NewContent(
    state: NewTaskState,
    onAction: (NewTaskAction) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 16.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SlideSelector(
                "New Task",
                "New Habit",
                "New Group",
                selected = 0
            ) {

            }

            NewTask(
                state = state,
                onAction = onAction
            )
        }
    }
}
