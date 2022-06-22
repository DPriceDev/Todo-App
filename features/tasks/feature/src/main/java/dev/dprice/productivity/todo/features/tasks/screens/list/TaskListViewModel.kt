package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListState
import dev.dprice.productivity.todo.features.tasks.usecase.DeleteTaskUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.GetTaskListUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface TaskListViewModel {
    val state: TaskListState

    fun updateState(action: TaskListAction)
}

@HiltViewModel
class TaskListViewModelImpl @Inject constructor(
    getTaskListUseCase: GetTaskListUseCase,
    private val deleteTaskListUseCase: DeleteTaskUseCase,
    private val updateTaskListUseCase: UpdateTaskUseCase
) : ViewModel(),
    TaskListViewModel {

    private val viewModelState = mutableStateOf(TaskListState(isLoading = true))
    override val state: TaskListState by viewModelState

    init {
        getTaskListUseCase()
            .map { tasks -> updateState(TaskListAction.UpdateTasks(tasks)) }
            .launchIn(viewModelScope)
    }

    override fun updateState(action: TaskListAction) {
        when (action) {
            is TaskListAction.CompleteTask -> updateTaskCompletion(action.id)
            is TaskListAction.DeleteTask -> viewModelScope.launch { deleteTaskListUseCase(action.id) }
            is TaskListAction.SelectTask -> selectTask(action.id)
            is TaskListAction.UpdateTasks -> viewModelState.value = state.copy(
                tasks = action.tasks,
                isLoading = false
            )
            TaskListAction.SearchButtonClicked -> viewModelState.value = state.copy(
                titleBarState = state.titleBarState.copy(isSearchShown = true)
            )
            is TaskListAction.UpdateSearchFocus -> updateSearchEntry(action.focus)
            is TaskListAction.UpdateSearchText -> viewModelState.value = state.copy(
                titleBarState = state.titleBarState.copy(
                    searchEntry = state.titleBarState.searchEntry.copy(value = action.value)
                )
            )
        }
    }

    private fun updateSearchEntry(newFocus: Boolean) {
        val isSearchShown = state.titleBarState.searchEntry.hasFocus
                && !newFocus
                && state.titleBarState.searchEntry.value.isEmpty()

        viewModelState.value = state.copy(
            titleBarState = state.titleBarState.copy(
                searchEntry = state.titleBarState.searchEntry.copy(hasFocus = newFocus),
                isSearchShown = if (isSearchShown) false else state.titleBarState.isSearchShown
            )
        )
    }

    private fun selectTask(id: String) {
        viewModelState.value = state.copy(
            selectedTaskId = if (id == state.selectedTaskId) null else id
        )
    }

    private fun updateTaskCompletion(id: String) {
        viewModelScope.launch {
            state.tasks.find { it.id == id }?.let {
                updateTaskListUseCase(
                    it.copy(isComplete = !it.isComplete)
                )
            }
        }
    }
}
