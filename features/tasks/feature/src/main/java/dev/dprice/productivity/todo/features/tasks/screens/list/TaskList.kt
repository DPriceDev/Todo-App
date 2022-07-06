package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListState
import dev.dprice.productivity.todo.features.tasks.screens.list.preview.TaskListStatePreviewProvider
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun TaskList(
    tasks: List<Task>,
    selectedTaskId: String?,
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    onAction: (TaskListAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        contentPadding = PaddingValues(
            top = 16.dp + topPadding,
            bottom = 4.dp,
            start = 0.dp,
            end = 0.dp
        )
    ) {
        itemsIndexed(
            items = tasks,
            key = { _, item -> item.id }
        ) { index, task ->
            TaskRow(
                task = task,
                index = index,
                //modifier = Modifier.animateItemPlacement(),
                onCompleteTaskClick = { onAction(TaskListAction.CompleteTask(task.id)) },
                onClicked = { onAction(TaskListAction.SelectTask(task.id)) },
                isSelected = task.id == selectedTaskId
            ) {
                ExpandedTaskContent(
                    task,
                    onCompleteTaskClick = { onAction(TaskListAction.CompleteTask(task.id)) },
                    onDeleteClicked = { onAction(TaskListAction.DeleteTask(task.id)) }
                )
            }
        }
        
        item {
            Spacer(modifier.height(96.dp))
        }
    }
}


@Preview
@Composable
private fun PreviewTaskList(
    @PreviewParameter(TaskListStatePreviewProvider::class) state: TaskListState
) {
    TodoAppTheme {
        TaskList(state.tasks, "") { }
    }
}