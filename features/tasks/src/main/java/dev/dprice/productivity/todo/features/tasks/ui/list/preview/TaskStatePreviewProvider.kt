package dev.dprice.productivity.todo.features.tasks.ui.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TaskStatePreviewProvider : PreviewParameterProvider<TaskState> {

    override val values: Sequence<TaskState> = sequenceOf(
        generateTask(),
        generateTask(isSelected = true),
        generateTask(isComplete = true),
        generateTask(isSelected = true, isComplete = true)
    )

    private fun generateTask(
        isSelected: Boolean = false,
        isComplete: Boolean = false
    ) = TaskState(
        Task(
            "Task unselected",
            "Description",
            isComplete = isComplete,
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ),
        isSelected = isSelected
    )
}