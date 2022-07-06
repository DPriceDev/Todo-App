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
import dev.dprice.productivity.todo.ui.components.ButtonState.DISABLED
import dev.dprice.productivity.todo.ui.components.ButtonState.ENABLED
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

    fun updateState(action: NewContentAction) = when (action) {
        is SelectContentType -> selectContentType(action)
        is UpdateForm -> updateForm(action.action)
        NewContentAction.ShowGroupOnly -> setOnlyGroupShown()
    }

    private fun setOnlyGroupShown() {
        viewModelState.value = state.copy(
            forms = listOf(NewGroupForm()),
            currentForm = NewGroupForm(),
            selectedForm = 0
        )
    }

    private fun selectContentType(action: SelectContentType) {
        viewModelState.value = state.copy(
            currentForm = state.forms[action.index],
            selectedForm = action.index
        )
    }

    private fun updateForm(action: FormAction<Type>) {
        val form = when (action) {
            is FormAction.UpdateFocus -> updateTextEntry(action.id, focus = action.focus)
            is FormAction.UpdateText -> updateTextEntry(action.id, text = action.text)
            is FormAction.ButtonClicked -> {
                submitForm(state.currentForm)
                return
            }
        }

        val checkedForm = updateButtonState(form)
        viewModelState.value = state.copy(
            forms = state.forms.toMutableList().apply { set(state.selectedForm, checkedForm) },
            currentForm = checkedForm
        )
    }

    private fun updateButtonState(form: ContentForm) = when (form) {
        is NewGroupForm -> form.copy(buttonState = if (form.isValid) ENABLED else DISABLED)
        is NewHabitForm -> form.copy(buttonState = if (form.isValid) ENABLED else DISABLED)
        is NewTaskForm -> form.copy(buttonState = if (form.isValid) ENABLED else DISABLED)
    }

    private fun updateTextEntry(
        id: Type,
        text: String? = null,
        focus: Boolean? = null
    ) = when (id) {
        Type.TITLE -> when (val form = state.currentForm) {
            is NewGroupForm -> form.copy(title = updateTitleUseCase(form.title, text, focus))
            is NewHabitForm -> form.copy(title = updateTitleUseCase(form.title, text, focus))
            is NewTaskForm -> form.copy(title = updateTitleUseCase(form.title, text, focus))
        }
        Type.DETAILS -> when (val form = state.currentForm) {
            is NewTaskForm -> form.copy(details = updateDetailsUseCase(form.details, text, focus))
            else -> state.currentForm
        }
        else -> state.currentForm
    }

    private fun submitForm(form: ContentForm) = when (form) {
        is NewTaskForm -> submitTask(form)
        is NewHabitForm -> submitHabit()
        is NewGroupForm -> submitGroup()
    }

    private fun submitTask(form: NewTaskForm) = viewModelScope.launch {
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
            currentForm = enabledForm,
            isDismissed = true
        )
    }

    private fun submitHabit() = viewModelScope.launch {
        // todo

    }

    private fun submitGroup() = viewModelScope.launch {
        // todo
    }
}