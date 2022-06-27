package dev.dprice.productivity.todo.features.tasks.screens.selector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.data.model.Colour
import dev.dprice.productivity.todo.features.tasks.data.model.Group
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorAction
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorState
import javax.inject.Inject

@HiltViewModel
class GroupSelectorViewModel @Inject constructor(): ViewModel() {

    private val viewModelState = mutableStateOf(
        GroupSelectorState(groups = listOf(
            Group(id = "sdfds", name = "All"),
            Group(id = "bgfsb", name = "Work", colour = Colour(150, 0, 150)),
            Group(id = "sevds", name = "Todo App", colour = Colour(0, 255, 0)),
        ))
    )
    val state: GroupSelectorState by viewModelState

    fun updateState(action: GroupSelectorAction) {

    }
}