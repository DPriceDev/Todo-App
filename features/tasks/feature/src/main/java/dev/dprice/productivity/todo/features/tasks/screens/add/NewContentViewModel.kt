package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.model.*
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction.SelectContentType
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction.UpdateForm
import dev.dprice.productivity.todo.features.tasks.usecase.AddTaskUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskDetailsEntryUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskTitleEntryUseCase
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
            is UpdateForm -> updateForm(action.action, dismissSheet)
        }
    }

    private fun selectContentType(action: SelectContentType) {
        viewModelState.value = viewState.copy(
            currentForm = viewState
                .forms
                .getOrNull(action.index)
                ?: viewState.forms.first()
        )
    }

    private fun updateForm(action: FormAction<NewContentEntry>, dismissSheet: () -> Unit) {
        viewModelState.value = viewState.copy(
            currentForm = when (action) {
                is FormAction.UpdateFocus -> updateEntryFocus(action.id, action.focus) ?: return
                is FormAction.UpdateText -> updateEntryText(action.id, action.text) ?: return
                is FormAction.ButtonClicked -> {
                    submitForm(viewState.currentForm, dismissSheet)
                    return
                }
            }
        )
    }

    private fun updateEntryFocus(id: NewContentEntry, focus: Boolean) = when (id) {
        NewContentEntry.TITLE -> when (val form = viewState.currentForm) {
            is NewTaskForm -> form.copy(title = updateTaskTitleEntryUseCase(form.title, focus))
            is NewGroupForm -> form.copy(title = updateTaskTitleEntryUseCase(form.title, focus))
            is NewHabitForm -> form.copy(title = updateTaskTitleEntryUseCase(form.title, focus))
        }
        NewContentEntry.DETAILS -> when (val form = viewState.currentForm) {
            is NewTaskForm -> form.copy(details = updateTaskDetailsEntryUseCase(form.details, focus))
            is NewGroupForm -> form.copy(details = updateTaskDetailsEntryUseCase(form.details, focus))
            else -> null
        }
        else -> null
    }

    private fun updateEntryText(id: NewContentEntry, text: String) = when (id) {
        NewContentEntry.TITLE -> when (val form = viewState.currentForm) {
            is NewTaskForm -> form.copy(title = updateTaskTitleEntryUseCase(form.title, text))
            is NewGroupForm -> form.copy(title = updateTaskTitleEntryUseCase(form.title, text))
            is NewHabitForm -> form.copy(title = updateTaskTitleEntryUseCase(form.title, text))
        }
        NewContentEntry.DETAILS -> when (val form = viewState.currentForm) {
            is NewTaskForm -> form.copy(details = updateTaskDetailsEntryUseCase(form.details, text))
            is NewGroupForm -> form.copy(details = updateTaskDetailsEntryUseCase(form.details, text))
            else -> null
        }
        else -> null
    }

    private fun submitForm(form: ContentForm, dismissSheet: () -> Unit) {
        when (form) {
            is NewTaskForm -> submitTask(form, dismissSheet)
            is NewHabitForm -> submitHabit(dismissSheet)
            is NewGroupForm -> submitGroup(dismissSheet)
        }
    }

    private fun submitTask(form: NewTaskForm, dismissSheet: () -> Unit) = viewModelScope.launch {
//        viewModelState.value = viewState.copy(
//            currentForm = viewState.currentForm.withEnablement(false)
//        )

        addTaskUseCase(
            form.title.value,
            form.details.value,
            Clock.System.now().toLocalDateTime(
                TimeZone.currentSystemDefault()
            )
        )

//        viewModelState.value = viewState.copy(
//            currentForm = viewState.currentForm.withEnablement(true),
//        )
        dismissSheet()
    }

    private fun submitHabit(dismissSheet: () -> Unit) = viewModelScope.launch {
        // todo
        dismissSheet()
    }

    private fun submitGroup(dismissSheet: () -> Unit) = viewModelScope.launch {
        // todo
        dismissSheet()
    }
}