package dev.dprice.productivity.todo.features.task.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.features.task.model.Task
import dev.dprice.productivity.todo.platform.theme.TodoAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskListUi(
    viewModel: TaskListViewModel = hiltViewModel<TaskListViewModelImpl>()
) {
    TaskList(
        viewModel.tasks,
        { },
        { }
    )
}

@Composable
fun TaskList(
    tasks: Flow<List<Task>>,
    onTaskClicked: (Task) -> Unit,
    onTaskUpdated: (Task) -> Unit
) {
    val taskState = tasks.collectAsState(initial = listOf())
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(taskState.value) { task ->
            TaskRow(
                task = task,
                onClicked = { onTaskClicked(task) },
                onUpdated = onTaskUpdated
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskRow(
    task: Task,
    onClicked: () -> Unit,
    onUpdated: (Task) -> Unit
) {
    Card(
        elevation = 4.dp,
        onClick = onClicked
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = task.title)
                Text(text = task.description)
                Text(text = task.dateTime.toString())
            }
            Checkbox(
                checked = task.isComplete,
                onCheckedChange = {
                    onUpdated(task.copy(isComplete = !task.isComplete))
                }
            )
        }
    }
}


/**
 * Previews
 */

private val testTask = Task(
    "Task",
    "Description",
    false,
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
)

private val testViewModel = object : TaskListViewModel {
    override val tasks: Flow<List<Task>> = flowOf(listOf(testTask))
}

@Preview
@Composable
private fun PreviewLayout() {
    TodoAppTheme {
        TaskListUi(
            testViewModel
        )
    }
}

@Preview
@Composable
private fun PreviewTaskList() {
    TodoAppTheme {
        TaskList(
            flowOf(listOf(testTask)),
            onTaskClicked = { },
            onTaskUpdated = { }
        )
    }
}

@Preview
@Composable
private fun PreviewTaskRow() {
    TodoAppTheme {
        TaskRow(
            testTask,
            { },
            { }
        )
    }
}