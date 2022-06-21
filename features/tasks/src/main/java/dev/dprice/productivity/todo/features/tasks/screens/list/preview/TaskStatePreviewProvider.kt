package dev.dprice.productivity.todo.features.tasks.screens.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.*

class TaskStatePreviewProvider : PreviewParameterProvider<TaskState> {

    override val values: Sequence<TaskState> = sequenceOf(
        generateTask(),
        generateTask(isSelected = true),
        generateTask(isComplete = true),
        generateTask(isSelected = true, isComplete = true),
        generateTask(isSwiped = true, isComplete = true)
    )

    private fun generateTask(
        isSelected: Boolean = false,
        isComplete: Boolean = false,
        isSwiped: Boolean = false
    ) = TaskState(
        Task(
            UUID.randomUUID().toString(),
            "Task unselected",
            "Description",
            isComplete = isComplete,
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ),
        isSelected = isSelected,
        isSwiped = isSwiped
    )
}
