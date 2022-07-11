package dev.dprice.productivity.todo.features.tasks.screens.add.group

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupAction
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupState
import javax.inject.Inject

@HiltViewModel
class NewGroupViewModel @Inject constructor(): ViewModel() {

    private val viewModelState = mutableStateOf(NewGroupState())
    val state: NewGroupState by viewModelState

    fun updateState(action: NewGroupAction) {

    }
}