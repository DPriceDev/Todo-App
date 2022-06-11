package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskListState
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskState
import dev.dprice.productivity.todo.ui.components.RoundedButton
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.shapes.waveToppedShape
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskListScreen(
    state: TaskListState,
    modifier: Modifier = Modifier,
    onAction: (TaskListAction) -> Unit
) {
    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = bottomSheetState
        ),
        sheetPeekHeight = 0.dp,
        sheetShape = waveToppedShape(
            100f
        ),
        sheetBackgroundColor = MediumBlue,
        sheetContentColor = Color.White,
        contentColor = Color.White,
        sheetContent = {
            NewTask()
        },
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

        val maxBackDropHeight = 80.dp
        var backDropHeight: Dp by remember {
            mutableStateOf(maxBackDropHeight)
        }
        with(LocalDensity.current) {
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
                backDropHeight = backDropHeight,
                waveHeight = 12.dp,
                waveOffsetPercent = waveOffset,
                backContent = {
                    TitleBar()
                }
            ) { padding ->
                TaskList(
                    tasks = state.tasks,
                    modifier = Modifier.nestedScroll(nestedScrollConnection),
                    topPadding = padding,
                    onAction = onAction
                )

                val scope = rememberCoroutineScope()
                NewTaskFabButton(
                    modifier = Modifier.align(alignment = Alignment.BottomEnd)
                ) {
                    scope.launch { bottomSheetState.expand() }
                }
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

@Composable
fun TaskList(
    tasks: List<TaskState>,
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    onAction: (TaskListAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        contentPadding = PaddingValues(
            top = 16.dp + topPadding,
            bottom = 4.dp,
            start = 4.dp,
            end = 4.dp
        )
    ) {
        items(tasks) { task ->
            TaskRow(
                task = task,
                onClicked = {
                    onAction(
                        TaskListAction.SelectTask(task)
                    )
                }
            )
        }
    }
}

@Composable
fun NewTask() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Test")
        Text("test 2")
        RoundedButton(
            text = "Add Task",
            onClick = { /*TODO*/ }
        )
    }
}

/**
 * Previews
 */

private val testTask = TaskState(
    Task(
        "Task",
        "Description",
        false,
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    )
)

private val testState = TaskListState(
    listOf(testTask)
)

@Preview
@Composable
private fun PreviewLayout() {
    TodoAppTheme {
        TaskListScreen(testState) { }
    }
}

@Preview
@Composable
private fun PreviewTaskList() {
    TodoAppTheme {
        TaskList(testState.tasks) { }
    }
}

@Preview
@Composable
private fun PreviewTaskRow() {
    TodoAppTheme {
        TaskRow(testTask) { }
    }
}

fun LocalDateTime.asTaskDateString(): String = "$dayOfMonth $month $year"
