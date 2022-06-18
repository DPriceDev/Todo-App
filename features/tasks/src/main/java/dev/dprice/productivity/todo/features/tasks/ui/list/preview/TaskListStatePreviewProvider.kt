package dev.dprice.productivity.todo.features.tasks.ui.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskListState
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TaskListStatePreviewProvider : PreviewParameterProvider<TaskListState> {

    private val testTask = TaskState(
        Task(
            "Task",
            "Description",
            false,
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    )

    override val values: Sequence<TaskListState> = sequenceOf(
        TaskListState(
            listOf(testTask)
        )
    )
}