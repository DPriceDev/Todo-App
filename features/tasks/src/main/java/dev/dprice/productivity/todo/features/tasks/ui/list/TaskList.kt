package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskListState
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskState
import dev.dprice.productivity.todo.features.tasks.ui.list.preview.TaskListStatePreviewProvider
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TaskList(
    tasks: List<TaskState>,
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
            start = 4.dp,
            end = 4.dp
        )
    ) {
        itemsIndexed(tasks) { index, task ->
            TaskRow(
                task = task,
           //     modifier = Modifier.animateItemPlacement(),
                index = index,
                onCompleteTaskClick = {
                    onAction(
                        TaskListAction.CompleteTask(task)
                    )
                },
                onSwipeTask = {
                    onAction(
                        TaskListAction.SwipeTask(task)
                    )
                },
                onClicked = {
                    onAction(
                        TaskListAction.SelectTask(task)
                    )
                }
            )
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
        TaskList(state.tasks) { }
    }
}