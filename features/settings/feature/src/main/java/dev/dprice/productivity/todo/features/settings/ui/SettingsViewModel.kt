package dev.dprice.productivity.todo.features.settings.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.features.settings.model.SettingsState
import dev.dprice.productivity.todo.platform.model.Component
import javax.inject.Inject
import javax.inject.Named

interface SettingsViewModel {
    val viewState: SettingsState
}

@HiltViewModel
class SettingsViewModelImpl @Inject constructor(
    @Named("Settings") components: Set<@JvmSuppressWildcards Component>
) : ViewModel(),
    SettingsViewModel {

    private val viewModelState: MutableState<SettingsState> = mutableStateOf(
        SettingsState(components.toList())
    )
    override val viewState: SettingsState by viewModelState
}
