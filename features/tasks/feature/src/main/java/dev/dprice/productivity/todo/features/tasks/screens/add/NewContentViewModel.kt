package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.model.*
import dev.dprice.productivity.todo.features.tasks.screens.add.model.ContentForm.Type
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction.SelectContentType
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction.UpdateForm
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
    private val updateDetailsUseCase: UpdateTaskDetailsEntryUseCase,
    private val updateTitleUseCase: UpdateTaskTitleEntryUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private var viewModelState = mutableStateOf(NewContentState())
    val state: NewContentState by viewModelState

    fun updateState(action: NewContentAction, dismissSheet: () -> Unit) {
        when (action) {
            is SelectContentType -> selectContentType(action)
            is UpdateForm -> updateForm(action.action, dismissSheet)
        }
    }

    private fun selectContentType(action: SelectContentType) {
        viewModelState.value = state.copy(
            currentForm = state.forms[action.index],
            selectedForm = action.index
        )
    }

    private fun updateForm(action: FormAction<Type>, dismissSheet: () -> Unit) {
        val newForm = when (action) {
            is FormAction.UpdateFocus -> updateTextEntry(action.id, focus = action.focus)
            is FormAction.UpdateText -> updateTextEntry(action.id, text = action.text)
            is FormAction.ButtonClicked -> {
                submitForm(state.currentForm, dismissSheet)
                return
            }
        }

        val checkedForm = when (newForm) {
            is NewGroupForm -> newForm.copy(buttonState = if (newForm.isValid) ButtonState.ENABLED else ButtonState.DISABLED)
            is NewHabitForm -> newForm.copy(buttonState = if (newForm.isValid) ButtonState.ENABLED else ButtonState.DISABLED)
            is NewTaskForm -> newForm.copy(buttonState = if (newForm.isValid) ButtonState.ENABLED else ButtonState.DISABLED)
        }
        viewModelState.value = state.copy(
            forms = state.forms.toMutableList().apply { set(state.selectedForm, checkedForm) },
            currentForm = checkedForm
        )
    }
    private fun updateTextEntry(
        id: Type,
        text: String? = null,
        focus: Boolean? = null
    ) = when(id) {
        Type.TITLE -> when(val form = state.currentForm) {
            is NewGroupForm -> form.copy(title = updateTitleUseCase(form.title, text, focus))
            is NewHabitForm -> form.copy(title = updateTitleUseCase(form.title, text, focus))
            is NewTaskForm -> form.copy(title = updateTitleUseCase(form.title, text, focus))
        }
        Type.DETAILS -> when(val form = state.currentForm) {
            is NewGroupForm -> form.copy(details = updateDetailsUseCase(form.details, text, focus))
            is NewTaskForm -> form.copy(details = updateDetailsUseCase(form.details, text, focus))
            else -> state.currentForm
        }
        else -> state.currentForm
    }

    private fun submitForm(form: ContentForm, dismissSheet: () -> Unit) = when (form) {
        is NewTaskForm -> submitTask(form, dismissSheet)
        is NewHabitForm -> submitHabit(dismissSheet)
        is NewGroupForm -> submitGroup(dismissSheet)
    }

    private fun submitTask(form: NewTaskForm, dismissSheet: () -> Unit) = viewModelScope.launch {
        val disabledForm = state.currentForm.withEnablement(false)
        viewModelState.value = state.copy(
            forms = state.forms.toMutableList().apply { set(state.selectedForm, disabledForm) },
            currentForm = disabledForm
        )

        addTaskUseCase(
            form.title.value,
            form.details.value,
            Clock.System.now().toLocalDateTime(
                TimeZone.currentSystemDefault()
            )
        )


        val enabledForm = state.currentForm.withEnablement(true)
        viewModelState.value = state.copy(
            forms = state.forms.apply { toMutableList().set(state.selectedForm, enabledForm) },
            currentForm = enabledForm
        )
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