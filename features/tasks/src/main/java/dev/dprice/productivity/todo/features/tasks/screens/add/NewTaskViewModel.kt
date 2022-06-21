package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewTaskAction
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewTaskForm
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewTaskState
import dev.dprice.productivity.todo.features.tasks.usecase.AddTaskUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskDetailsEntryUseCase
import dev.dprice.productivity.todo.features.tasks.usecase.UpdateTaskTitleEntryUseCase
import dev.dprice.productivity.todo.ui.components.ButtonState
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

interface NewTaskViewModel {
    val viewState: NewTaskState

    fun updateState(
        action: NewTaskAction,
        dismissSheet: () -> Unit
    )
}

@HiltViewModel
class NewTaskViewModelImpl @Inject constructor(
    private val updateTaskDetailsEntryUseCase: UpdateTaskDetailsEntryUseCase,
    private val updateTaskTitleEntryUseCase: UpdateTaskTitleEntryUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel(),
    NewTaskViewModel {

    private var viewModelState = mutableStateOf(NewTaskState())
    override val viewState: NewTaskState by viewModelState

    override fun updateState(
        action: NewTaskAction,
        dismissSheet: () -> Unit
    ) {
        val newState = when (action) {
            is NewTaskAction.UpdateDescriptionFocus -> viewState.copy(
                form = viewState.form.copy(
                    detailsEntry = updateTaskDetailsEntryUseCase(viewState.form.detailsEntry, action.focus)
                )
            )
            is NewTaskAction.UpdateDescriptionValue -> viewState.copy(
                form = viewState.form.copy(
                detailsEntry = updateTaskDetailsEntryUseCase(viewState.form.detailsEntry, action.value)
                )
            )
            is NewTaskAction.UpdateTitleFocus -> viewState.copy(
                form = viewState.form.copy(
                titleEntry = updateTaskTitleEntryUseCase(viewState.form.titleEntry, action.focus)
                )
            )
            is NewTaskAction.UpdateTitleValue -> viewState.copy(
                form = viewState.form.copy(
                titleEntry = updateTaskTitleEntryUseCase(viewState.form.titleEntry, action.value)
                )
            )
            NewTaskAction.CreateTask -> {
                viewModelScope.launch {
                    viewModelState.value = viewState.copy(
                        buttonState = ButtonState.LOADING,
                        form = viewState.form.copy(
                            titleEntry = viewState.form.titleEntry.copy(enabled = false),
                            detailsEntry = viewState.form.detailsEntry.copy(enabled = false),
                        )
                    )
                    val form = viewState.form
                    addTaskUseCase(
                        form.titleEntry.value,
                        form.detailsEntry.value,
                         Clock.System.now().toLocalDateTime(
                            TimeZone.currentSystemDefault()
                        )
                    )

                    // todo: show created animation

                    viewModelState.value = viewState.copy(
                        buttonState = ButtonState.ENABLED,
                        form = viewState.form.copy(
                            titleEntry = viewState.form.titleEntry.copy(enabled = true),
                            detailsEntry = viewState.form.detailsEntry.copy(enabled = true),
                        )
                    )
                    dismissSheet()
                }

                viewState
            }
        }

        viewModelState.value = newState.copy(
            buttonState = if (newState.form.isValid) {
                ButtonState.ENABLED
            } else {
                ButtonState.DISABLED
            }
        )
    }

    private val NewTaskForm.isValid
        get() = titleEntry.isValid && detailsEntry.isValid
}