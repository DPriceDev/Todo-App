package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction.*
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentState
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentType
import dev.dprice.productivity.todo.features.tasks.usecase.AddTaskUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskDetailsEntryUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskTitleEntryUseCase
import dev.dprice.productivity.todo.ui.components.ButtonState
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
            is NewTaskAction -> updateTaskForm(action)
            is NewGroupAction -> updateGroupForm(action)
            is SelectContentType -> selectContentType(action)
            SubmitClicked -> submitForm(dismissSheet)
        }
    }

    private fun submitForm(dismissSheet: () -> Unit) = viewModelScope.launch {
        viewModelState.value = viewState.copy(buttonState = ButtonState.LOADING)

       val isSuccessful = when (viewState.selectedContentType) {
            NewContentType.TASK -> submitTask()
            NewContentType.HABIT -> submitHabit()
            NewContentType.GROUP -> submitGroup()
        }

        if (isSuccessful) dismissSheet()
        viewModelState.value = viewState.copy(buttonState = ButtonState.ENABLED)
    }

    private fun submitGroup() : Boolean {
        // todo
        return false
    }

    private fun submitHabit() : Boolean {
        // todo
        return false
    }

    private suspend fun submitTask() : Boolean {
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
            taskForm = viewState.taskForm.withEnablement(true)
        )

        return true
    }

    private fun selectContentType(action: SelectContentType) {
        viewModelState.value = viewState.copy(
            selectedContentType = viewState
                .contentTypes
                .getOrNull(action.index)
                ?: viewState.contentTypes.first()
        )
    }

    private fun updateGroupForm(action: NewGroupAction) {
        val form = when (action) {
            is NewGroupAction.UpdateDescriptionFocus -> viewState.taskForm.copy(
                detailsEntry = updateTaskDetailsEntryUseCase(viewState.taskForm.detailsEntry, action.focus)
            )
            is NewGroupAction.UpdateDescriptionValue -> viewState.taskForm.copy(
                detailsEntry = updateTaskDetailsEntryUseCase(viewState.taskForm.detailsEntry, action.value)
            )
            is NewGroupAction.UpdateTitleFocus -> viewState.taskForm.copy(
                titleEntry = updateTaskTitleEntryUseCase(viewState.taskForm.titleEntry, action.focus)
            )
            is NewGroupAction.UpdateTitleValue -> viewState.taskForm.copy(
                titleEntry = updateTaskTitleEntryUseCase(viewState.taskForm.titleEntry, action.value)
            )
        }

        updateState(viewState.copy(groupForm = form), isValid = form.isValid)
    }

    private fun updateTaskForm(action: NewTaskAction) {
        val form = when (action) {
            is NewTaskAction.UpdateDescriptionFocus -> viewState.taskForm.copy(
                detailsEntry = updateTaskDetailsEntryUseCase(viewState.taskForm.detailsEntry, action.focus)
            )
            is NewTaskAction.UpdateDescriptionValue -> viewState.taskForm.copy(
                detailsEntry = updateTaskDetailsEntryUseCase(viewState.taskForm.detailsEntry, action.value)
            )
            is NewTaskAction.UpdateTitleFocus -> viewState.taskForm.copy(
                titleEntry = updateTaskTitleEntryUseCase(viewState.taskForm.titleEntry, action.focus)
            )
            is NewTaskAction.UpdateTitleValue -> viewState.taskForm.copy(
                titleEntry = updateTaskTitleEntryUseCase(viewState.taskForm.titleEntry, action.value)
            )
        }

        updateState(viewState.copy(taskForm = form), isValid = form.isValid)
    }

    private fun updateState(state: NewContentState, isValid: Boolean) {
        viewModelState.value = state.copy(
            buttonState = if (isValid) ButtonState.ENABLED else ButtonState.DISABLED
        )
    }
}