package dev.dprice.productivity.todo.features.task.ui.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.task.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

interface TaskListViewModel {
    val tasks: Flow<List<Task>>
}

@HiltViewModel
class TaskListViewModelImpl @Inject constructor() : ViewModel(), TaskListViewModel {

    override val tasks: Flow<List<Task>> = flowOf(
        listOf(
            Task(
                "Task",
                "Description",
                false,
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Task(
                "Task 2",
                "Description",
                false,
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            Task(
                "Task 3",
                "Description",
                true,
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
    )
}