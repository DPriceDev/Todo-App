package dev.dprice.productivity.todo.features.tasks.screens.selector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.data.model.TaskGroup
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorAction
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorState
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.SelectorGroup
import dev.dprice.productivity.todo.features.tasks.usecase.GetAllTaskGroupsUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.MarkGroupsAsDeletedUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.MarkGroupsAsNotDeletedUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.SetCurrentGroupUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

// todo: Duplicate group
// todo: How to delete the group eventually? should call delete after snackbar?
@HiltViewModel
class GroupSelectorViewModel @Inject constructor(
    getTaskGroupsUseCase: GetAllTaskGroupsUseCase,
    private val setCurrentGroupUseCase: SetCurrentGroupUseCase,
    private val markGroupsAsDeletedUseCase: MarkGroupsAsDeletedUseCase,
    private val markGroupsAsNotDeletedUseCase: MarkGroupsAsNotDeletedUseCase
): ViewModel() {

    private var lastDeletedGroupIds: List<String> = emptyList()
    private val messageFlow = MutableSharedFlow<String>()
    private val viewModelState = mutableStateOf(
        GroupSelectorState(messageFlow = messageFlow)
    )
    val state: GroupSelectorState by viewModelState

    init {
        getTaskGroupsUseCase()
            .onEach(::updateGroups)
            .launchIn(viewModelScope)
    }

    fun onAction(action: GroupSelectorAction) {
        when (action) {
            is GroupSelectorAction.LongPressGroup -> onLongPress(action.id)
            is GroupSelectorAction.SelectGroup -> if (state.isEditMode) {
                selectEditGroups(action.id)
            } else {
                selectGroup(action.id)
            }
            GroupSelectorAction.DeleteGroups -> deleteGroups()
            GroupSelectorAction.ExitEditMode -> exitEditMode()
            GroupSelectorAction.UndoDelete -> unDeleteGroups()
        }
    }

    private fun selectGroup(id: String?) {
        viewModelScope.launch {
            try {
                setCurrentGroupUseCase(id)
            } catch (exception: IOException) {
                Timber.e(exception, "Exception thrown setting current group")
            }
            viewModelState.value = state.copy(isDismissed = true)
        }
    }

    private fun selectEditGroups(id: String?) {
        viewModelState.value = state.copy(
            groups = id?.let {
                state.groups.map { group ->
                    group.copy(
                        isSelected = if (id == group.group?.id) {
                            !group.isSelected
                        } else {
                            group.isSelected
                        }
                    )
                }
            } ?: state.groups
        )
    }

    private fun onLongPress(id: String?) {
        if (!state.isEditMode) {
            viewModelState.value = state.copy(
                groups = state.groups.map {
                    it.copy(isSelected = it.group?.id == id && id != null)
                },
                isEditMode = true
            )
        }
    }

    private fun deleteGroups() {
        viewModelScope.launch {
            val ids = state.groups
                .filter { it.isSelected }
                .mapNotNull { it.group?.id }

            lastDeletedGroupIds = ids
            markGroupsAsDeletedUseCase(ids)
            messageFlow.emit("${ ids.size } Group${ if (ids.size > 1) "s" else "" } Deleted") // todo: Plural string?
            exitEditMode()
        }
    }

    private fun unDeleteGroups() {
        viewModelScope.launch {
            markGroupsAsNotDeletedUseCase(lastDeletedGroupIds)
            lastDeletedGroupIds = emptyList()
            exitEditMode()
        }
    }

    private fun exitEditMode() {
        viewModelState.value = state.copy(
            groups = state.groups.map { it.copy(isSelected = false) },
            isEditMode = false
        )
    }

    private fun updateGroups(groups: List<TaskGroup>) {
        viewModelState.value = state.copy(
            groups = groups.map {
                SelectorGroup(it.group, it.tasks.count())
            }
        )
    }
}