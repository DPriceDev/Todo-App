package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListState
import dev.dprice.productivity.todo.features.tasks.screens.list.preview.TaskListStatePreviewProvider
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun TaskListScreen(
    state: TaskListState,
    modifier: Modifier = Modifier,
    maxBackDropHeight: Dp = 264.dp,
    openAddTaskSheet: () -> Unit,
    onAction: (TaskListAction) -> Unit,
) {
    val waveOffset by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 10000,
                easing = LinearEasing
            )
        )
    )

    var backDropHeight: Dp by remember {
        mutableStateOf(maxBackDropHeight)
    }
    with(LocalDensity.current) {
        // Provides scrolling to title bar from lazy list
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    val delta = available.y.toDp()
                    val newHeight = backDropHeight + delta
                    backDropHeight = newHeight.coerceIn(0.dp..maxBackDropHeight)

                    val consumed = when {
                        newHeight < 0.dp -> delta - delta - newHeight
                        newHeight > maxBackDropHeight -> delta - delta - (newHeight - maxBackDropHeight)
                        else -> delta
                    }

                    return Offset(0f, consumed.toPx())
                }
            }
        }

        WavyBackdropScaffold(
            modifier = modifier,
            backDropHeight = backDropHeight,
            waveHeight = 12.dp,
            waveOffsetPercent = waveOffset,
            backContent = {
                TitleBar(state.titleBarState, onAction)
            }
        ) { padding ->
            TaskList(
                tasks = state.tasks,
                selectedTaskId = state.selectedTaskId,
                modifier = Modifier.nestedScroll(nestedScrollConnection),
                topPadding = padding,
                onAction = onAction
            )

            NewTaskFabButton(
                modifier = Modifier.align(alignment = Alignment.BottomEnd)
            ) {
                openAddTaskSheet()
            }
        }
    }
}

@Composable
private fun NewTaskFabButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .padding(24.dp)
            .then(modifier)
    ) {
        Icon(
            Icons.Default.Add,
            "Add Task"
        )
    }
}

/**
 * Previews
 */

@Preview
@Composable
private fun PreviewTaskListScreen(
    @PreviewParameter(TaskListStatePreviewProvider::class) state: TaskListState
) {
    TodoAppTheme {
        TaskListScreen(state, openAddTaskSheet = { }) {}
    }
}
