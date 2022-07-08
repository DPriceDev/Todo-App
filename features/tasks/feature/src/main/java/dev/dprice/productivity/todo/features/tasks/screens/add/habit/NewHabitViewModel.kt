package dev.dprice.productivity.todo.features.tasks.screens.add.habit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.habit.model.NewHabitAction
import dev.dprice.productivity.todo.features.tasks.screens.add.habit.model.NewHabitState
import javax.inject.Inject

@HiltViewModel
class NewHabitViewModel @Inject constructor(): ViewModel() {

    private val viewModelState = mutableStateOf(NewHabitState())
    val state: NewHabitState by viewModelState

    fun updateState(action: NewHabitAction) {

    }
}