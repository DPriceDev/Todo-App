package dev.dprice.productivity.todo.features.tasks.ui.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.ui.add.model.NewTaskAction
import dev.dprice.productivity.todo.features.tasks.ui.add.model.NewTaskState
import javax.inject.Inject

interface NewTaskViewModel {
    val viewState: NewTaskState

    fun updateState(action: NewTaskAction)
}

@HiltViewModel
class NewTaskViewModelImpl @Inject constructor() : ViewModel(), NewTaskViewModel {

    private val viewModelState = mutableStateOf(NewTaskState())
    override val viewState: NewTaskState by viewModelState

    override fun updateState(action: NewTaskAction) {

    }
}