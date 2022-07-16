package dev.dprice.productivity.todo.features.groups.feature.screens.form

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.groups.data.model.Colour
import dev.dprice.productivity.todo.features.groups.data.model.Icon
import dev.dprice.productivity.todo.features.groups.feature.screens.form.model.NewGroupAction
import dev.dprice.productivity.todo.features.groups.feature.screens.form.model.NewGroupState
import dev.dprice.productivity.todo.features.groups.feature.usecase.UpdateGroupTitleEntryUseCase
import dev.dprice.productivity.todo.features.groups.usecase.CreateGroupUseCase
import dev.dprice.productivity.todo.ui.components.buttons.ButtonState
import dev.dprice.productivity.todo.ui.components.text.EntryField
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGroupViewModel @Inject constructor(
    private val updateTitleEntryUseCase: UpdateGroupTitleEntryUseCase,
    private val createGroupUseCase: CreateGroupUseCase
): ViewModel() {

    private val viewModelState = mutableStateOf(NewGroupState())
    val state: NewGroupState by viewModelState

    fun updateState(action: NewGroupAction) {
        when (action) {
            is NewGroupAction.SelectTab -> selectTab(action.tab)
            NewGroupAction.SubmitForm -> createGroup()
            is NewGroupAction.UpdateTitleFocus -> updateTitleFocus(action.focus)
            is NewGroupAction.UpdateTitleText -> updateTitleText(action.text)
            is NewGroupAction.SelectColour -> selectColour(action.colour)
            is NewGroupAction.SelectIcon -> selectIcon(action.icon)
        }
    }

    private fun updateTitleFocus(newFocus: Boolean) {
        val entry = updateTitleEntryUseCase(state.title,newFocus = newFocus)
        updateTitleEntry(entry)
    }

    private fun updateTitleText(newText: String) {
        val entry = updateTitleEntryUseCase(state.title, newTitle = newText)
        updateTitleEntry(entry)
    }

    private fun updateTitleEntry(entry: EntryField) {
        viewModelState.value = state.copy(
            title = entry,
            buttonState = if (state.isValid) ButtonState.ENABLED else ButtonState.DISABLED
        )
    }

    private fun selectTab(tab: NewGroupState.GroupTab?) {
        viewModelState.value = state.copy(selectedTab = tab)
    }

    private fun selectColour(colour: Colour) {
        viewModelState.value = state.copy(colour = colour)
    }

    private fun selectIcon(icon: Icon) {
        viewModelState.value = state.copy(icon = icon)
    }

    private fun createGroup() {
        viewModelState.value = state.withEnablement(enabled = false)

        viewModelScope.launch {
            createGroupUseCase(
                state.title.value,
                state.colour,
                state.icon
            )

            viewModelState.value = state.copy(
                isDismissed = true
            ).withEnablement(enabled = true)
        }
    }
}