package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.model.*
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction.*
import dev.dprice.productivity.todo.features.tasks.usecase.AddTaskUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskDetailsEntryUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskTitleEntryUseCase
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.FormAction
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class NewContentViewModel @Inject constructor(
    private val updateTaskDetailsEntryUseCase: UpdateTaskDetailsEntryUseCase,
    private val updateTaskTitleEntryUseCase: UpdateTaskTitleEntryUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private var viewModelState = mutableStateOf(NewContentState())
    val viewState: NewContentState by viewModelState

    fun updateState(action: NewContentAction, dismissSheet: () -> Unit) {
        when (action) {
            is SelectContentType -> selectContentType(action)
            is UpdateGroupForm -> updateGroupForm(action.action, dismissSheet)
            is UpdateHabitForm -> updateHabitForm(action.action, dismissSheet)
            is UpdateTaskForm -> updateTaskForm(action.action, dismissSheet)
        }
    }

    private fun selectContentType(action: SelectContentType) {
        viewModelState.value = viewState.copy(
            selectedContentType = viewState
                .contentTypes
                .getOrNull(action.index)
                ?: viewState.contentTypes.first()
        )
    }

    private fun updateTaskForm(action: FormAction<NewTaskForm.Type>, dismissSheet: () -> Unit) {
        val form = when (action) {
            is FormAction.UpdateFocus -> when(action.id) {
                NewTaskForm.Type.TITLE -> viewState.taskForm.copy(
                    titleEntry = updateTaskTitleEntryUseCase(viewState.taskForm.titleEntry, action.focus)
                )
                NewTaskForm.Type.DETAILS -> viewState.taskForm.copy(
                    detailsEntry = updateTaskDetailsEntryUseCase(viewState.taskForm.detailsEntry, action.focus)
                )
                else -> return
            }
            is FormAction.UpdateText ->  when(action.id) {
                NewTaskForm.Type.TITLE -> viewState.taskForm.copy(
                    titleEntry = updateTaskTitleEntryUseCase(viewState.taskForm.titleEntry, action.text)
                )
                NewTaskForm.Type.DETAILS -> viewState.taskForm.copy(
                    detailsEntry = updateTaskDetailsEntryUseCase(viewState.taskForm.detailsEntry, action.text)
                )
                else -> return
            }
            is FormAction.ButtonClicked -> {
                submitTask(dismissSheet)
                return
            }
        }

        viewModelState.value = viewState.copy(
            taskForm = form.copy(
                buttonState = if (form.isValid) ButtonState.ENABLED else ButtonState.DISABLED
            )
        )
    }

    private fun updateHabitForm(action: FormAction<NewHabitForm.Type>, dismissSheet: () -> Unit) {

    }

    private fun updateGroupForm(action: FormAction<NewGroupForm.Type>, dismissSheet: () -> Unit) {
        val form = when (action) {
            is FormAction.UpdateFocus -> when(action.id) {
                NewGroupForm.Type.TITLE -> viewState.groupForm.copy(
                    titleEntry = updateTaskTitleEntryUseCase(viewState.groupForm.titleEntry, action.focus)
                )
                NewGroupForm.Type.DETAILS -> viewState.groupForm.copy(
                    detailsEntry = updateTaskDetailsEntryUseCase(viewState.groupForm.detailsEntry, action.focus)
                )
                else -> return
            }
            is FormAction.UpdateText ->  when(action.id) {
                NewGroupForm.Type.TITLE -> viewState.groupForm.copy(
                    titleEntry = updateTaskTitleEntryUseCase(viewState.groupForm.titleEntry, action.text)
                )
                NewGroupForm.Type.DETAILS -> viewState.groupForm.copy(
                    detailsEntry = updateTaskDetailsEntryUseCase(viewState.groupForm.detailsEntry, action.text)
                )
                else -> return
            }
            is FormAction.ButtonClicked -> {
                submitGroup(dismissSheet)
                return
            }
        }

        viewModelState.value = viewState.copy(
            groupForm = form.copy(
                buttonState = if (form.isValid) ButtonState.ENABLED else ButtonState.DISABLED
            )
        )
    }

    private fun submitGroup(dismissSheet: () -> Unit) = viewModelScope.launch {
        // todo
        dismissSheet()
    }

    private fun submitHabit(dismissSheet: () -> Unit) = viewModelScope.launch {
        // todo
        dismissSheet()
    }

    private fun submitTask(dismissSheet: () -> Unit) = viewModelScope.launch {
        viewModelState.value = viewState.copy(
            taskForm = viewState.taskForm.withEnablement(false)
        )

        val form = viewState.taskForm
        addTaskUseCase(
            form.titleEntry.value,
            form.detailsEntry.value,
            Clock.System.now().toLocalDateTime(
                TimeZone.currentSystemDefault()
            )
        )

        viewModelState.value = viewState.copy(
            taskForm = viewState.taskForm.withEnablement(true),
        )
        dismissSheet()
    }
}