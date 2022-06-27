package dev.dprice.productivity.todo.features.tasks.screens.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.*

class TaskListStatePreviewProvider : PreviewParameterProvider<TaskListState> {

    private val testTask = Task(
        UUID.randomUUID().toString(),
        "Task",
        "Description",
        finishDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        isCompleted = false,
    )

    override val values: Sequence<TaskListState> = sequenceOf(
        TaskListState(
            listOf(testTask)
        )
    )
}