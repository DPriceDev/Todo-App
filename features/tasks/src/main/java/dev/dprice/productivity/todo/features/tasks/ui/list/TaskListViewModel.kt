package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskListState
import dev.dprice.productivity.todo.features.tasks.ui.list.model.asTaskState
import dev.dprice.productivity.todo.features.tasks.usecase.GetTaskListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

interface TaskListViewModel {
    val state: TaskListState

    fun updateState(action: TaskListAction)
}

@HiltViewModel
class TaskListViewModelImpl @Inject constructor(
    private val getTaskListUseCase: GetTaskListUseCase
) : ViewModel(),
    TaskListViewModel {

    private val viewState: MutableState<TaskListState> = mutableStateOf(
        TaskListState(isLoading = true)
    )
    override val state: TaskListState by viewState

    val tasks: Flow<List<Task>> = flowOf(
        buildList {
            repeat(20) {
                add(
                    Task(
                        LoremIpsum(5).values.joinToString() + " $it",
                        LoremIpsum(20).values.joinToString(),
                        false,
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                )
            }
        }
    )

    init {
        viewModelScope.launch {
            tasks.collect {
                viewState.value = state.copy(
                    tasks = it.map { task -> task.asTaskState() },
                    isLoading = false
                )
            }
        }
    }

    override fun updateState(action: TaskListAction) {
        viewState.value = when (action) {
            is TaskListAction.AddTask -> TODO()
            is TaskListAction.CompleteTask -> TODO()
            is TaskListAction.DeleteTask -> state.copy(
                tasks = state.tasks.apply { toMutableList().remove(action.task) }
            )
            is TaskListAction.SelectTask -> state.copy(
                tasks = state.tasks.map { task ->
                    task.copy(
                        isSelected = action.task == task
                    )
                }
            )
            is TaskListAction.UnCompleteTask -> TODO()
            is TaskListAction.UpdateTasks -> state.copy(
                tasks = action.tasks,
                isLoading = false
            )
        }
    }
}
