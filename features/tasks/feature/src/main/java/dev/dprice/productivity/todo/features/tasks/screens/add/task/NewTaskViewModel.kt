package dev.dprice.productivity.todo.features.tasks.screens.add.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.task.model.NewTaskAction
import dev.dprice.productivity.todo.features.tasks.screens.add.task.model.NewTaskState
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor() : ViewModel() {

    private val viewModelState = mutableStateOf(NewTaskState())
    val state: NewTaskState by viewModelState

    fun updateState(action: NewTaskAction) {

    }
}