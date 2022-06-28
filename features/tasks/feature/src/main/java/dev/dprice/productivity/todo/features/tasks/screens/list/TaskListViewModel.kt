package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskFilter
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListState
import dev.dprice.productivity.todo.features.tasks.usecase.DeleteTaskUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.GetCurrentTasksUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

interface TaskListViewModel {
    val state: TaskListState

    fun updateState(action: TaskListAction)
}

@HiltViewModel
class TaskListViewModelImpl @Inject constructor(
    getCurrentTasksUseCase: GetCurrentTasksUseCase,
    private val deleteTaskListUseCase: DeleteTaskUseCase,
    private val updateTaskListUseCase: UpdateTaskUseCase
) : ViewModel(),
    TaskListViewModel {

    private val searchFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val filterFlow: MutableStateFlow<TaskFilter> = MutableStateFlow(TaskFilter.ALL)
    private val viewModelState = mutableStateOf(TaskListState(isLoading = true))
    override val state: TaskListState by viewModelState

    init {
        getCurrentTasksUseCase()
            .combine(searchFlow) { tasks, searchText ->
                tasks.filter { it.title.contains(searchText) }
            }.combine(filterFlow) { tasks, filter ->
                tasks.filter { task ->
                    when(filter) {
                        TaskFilter.COMPLETE -> task.isCompleted
                        TaskFilter.INCOMPLETE -> !task.isCompleted
                        else -> true
                    }
                }
            }
            .onEach { tasks -> updateState(TaskListAction.UpdateTasks(tasks)) }
            .launchIn(viewModelScope)
    }

    override fun updateState(action: TaskListAction) {
        when (action) {
            is TaskListAction.CompleteTask -> updateTaskCompletion(action.id)
            is TaskListAction.DeleteTask -> deleteTask(action.id)
            is TaskListAction.SelectTask -> selectTask(action.id)
            is TaskListAction.UpdateTasks -> updateTasks(action.tasks)
            TaskListAction.SearchButtonClicked -> openSearch()
            is TaskListAction.UpdateSearchFocus -> updateSearchEntry(action.focus)
            is TaskListAction.UpdateSearchText -> updateSearchEntry(action.value)
            is TaskListAction.UpdateFilter -> updateTaskFilter(action.filter)
        }
    }

    private fun updateTaskFilter(filter: TaskFilter) {
        filterFlow.value = filter
        viewModelState.value = state.copy(
            titleBarState = state.titleBarState.copy(filter = filter)
        )
    }

    private fun updateTaskCompletion(id: String) {
        viewModelScope.launch {
            state.tasks.find { it.id == id }?.let {
                updateTaskListUseCase(
                    it.copy(isCompleted = !it.isCompleted)
                )
            }
        }
    }

    private fun deleteTask(id: String) = viewModelScope.launch {
        deleteTaskListUseCase(id)
    }

    private fun selectTask(id: String) {
        viewModelState.value = state.copy(
            selectedTaskId = if (id == state.selectedTaskId) null else id
        )
    }

    private fun updateTasks(tasks: List<Task>) {
        viewModelState.value = state.copy(
            tasks = tasks,
            isLoading = false
        )
    }

    private fun openSearch() {
        viewModelState.value = state.copy(
            titleBarState = state.titleBarState.copy(isSearchShown = true)
        )
    }

    private fun updateSearchEntry(text: String) {
        searchFlow.value = text
        viewModelState.value = state.copy(
            titleBarState = state.titleBarState.copy(
                searchEntry = state.titleBarState.searchEntry.copy(value = text)
            )
        )
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
}
