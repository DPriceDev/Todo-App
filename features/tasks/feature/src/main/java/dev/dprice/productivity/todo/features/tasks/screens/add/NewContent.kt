package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentState
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentType
import dev.dprice.productivity.todo.ui.components.RoundedButton
import dev.dprice.productivity.todo.ui.components.SlideSelector
import dev.dprice.productivity.todo.ui.theme.Yellow

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

            when (state.selectedContentType) {
                NewContentType.TASK -> {
                    NewTask(
                        form = state.taskForm,
                        onAction = onAction
                    )
                }
                NewContentType.HABIT -> {
                    NewHabit(
//                        state = state,
//                        onAction = onAction
                    )
                }
                NewContentType.GROUP -> {
                    NewGroup(
//                        state = state,
//                        onAction = onAction
                    )
                }
            }

            Divider(
                color = Yellow,
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            RoundedButton(
                text = "Create",
                buttonState = state.buttonState,
                modifier = Modifier.focusable(),
                onClick = { onAction(NewContentAction.SubmitClicked) }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
