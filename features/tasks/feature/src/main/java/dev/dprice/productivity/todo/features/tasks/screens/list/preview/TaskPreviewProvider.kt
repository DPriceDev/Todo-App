package dev.dprice.productivity.todo.features.tasks.screens.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.*

class TaskPreviewProvider : PreviewParameterProvider<Task> {

    override val values: Sequence<Task> = sequenceOf(
        generateTask(),
        generateTask(isComplete = true)
    )

    private fun generateTask(
        isComplete: Boolean = false
    ) = Task(
        UUID.randomUUID().toString(),
        "Task unselected",
        "Description",
        isComplete = isComplete,
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    )
}

