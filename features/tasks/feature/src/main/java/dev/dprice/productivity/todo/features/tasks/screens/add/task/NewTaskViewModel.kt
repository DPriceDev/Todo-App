package dev.dprice.productivity.todo.features.tasks.screens.add.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.task.model.NewTaskAction
import dev.dprice.productivity.todo.features.tasks.screens.add.task.model.NewTaskState
import dev.dprice.productivity.todo.ui.components.text.EntryField
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor() : ViewModel() {

    private val viewModelState = mutableStateOf(NewTaskState())
    val state: NewTaskState by viewModelState

    fun updateState(action: NewTaskAction) {
        when (action) {
            NewTaskAction.SubmitForm -> createTask()
            is NewTaskAction.UpdateDetailsFocus -> updateDetails(action.newFocus)
            is NewTaskAction.UpdateDetailsText -> updateDetails(action.newText)
            is NewTaskAction.UpdateTitleFocus -> updateTitle(action.newFocus)
            is NewTaskAction.UpdateTitleText -> updateTitle(action.newText)
        }
    }

    private fun updateTitle(newText: String) {
    }

    private fun updateTitle(newFocus: Boolean) {
    }

    private fun updateDetails(newText: String) {
    }

    private fun updateDetails(newFocus: Boolean) {
    }

    private fun updateEntry(entry: EntryField) {

    }

    fun createTask() {

    }
}