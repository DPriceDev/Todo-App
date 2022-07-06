package dev.dprice.productivity.todo.features.tasks.screens.selector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.data.model.Group
import dev.dprice.productivity.todo.features.tasks.data.model.TaskGroup
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorState
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.SelectorGroup
import dev.dprice.productivity.todo.features.tasks.usecase.GetAllTaskGroupsUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.SetCurrentGroupUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GroupSelectorViewModel @Inject constructor(
    getTaskGroupsUseCase: GetAllTaskGroupsUseCase,
    private val setCurrentGroupUseCase: SetCurrentGroupUseCase
): ViewModel() {

    private val viewModelState = mutableStateOf(GroupSelectorState())
    val state: GroupSelectorState by viewModelState

    init {
        getTaskGroupsUseCase()
            .onEach(::updateGroups)
            .launchIn(viewModelScope)
    }

    fun selectGroup(group: Group?) {
        viewModelScope.launch {
            try {
                setCurrentGroupUseCase(group?.id)
            } catch (exception: IOException) {
                Timber.e(exception, "Exception thrown setting current group")
            }
            viewModelState.value = state.copy(isDismissed = true)
        }
    }

    private fun updateGroups(groups: List<TaskGroup>) {
        viewModelState.value = state.copy(
            groups = groups.map {
                SelectorGroup(it.group, it.tasks.count())
            }
        )
    }
}