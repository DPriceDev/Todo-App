package dev.dprice.productivity.todo.main.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.AuthenticationSource
import dev.dprice.productivity.todo.core.DataState
import dev.dprice.productivity.todo.main.model.MainState
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MainViewModel {
    val viewState: MainState
}

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : ViewModel(),
    MainViewModel {

    private val viewModelState: MutableState<MainState> = mutableStateOf(
        MainState(isLoading = true)
    )
    override val viewState: MainState by viewModelState

    init {
        observeAuthState()
    }

    private fun observeAuthState() {
        viewModelScope.launch {
            authenticationSource.getCurrentSession().collect { state ->
                when (state) {
                    is DataState.Data -> {
                        viewModelState.value = viewState.copy(
                            isLoading = false,
                            userSession = state.value
                        )
                    }
                    is DataState.Error -> TODO()
                    DataState.Loading -> {
                        viewModelState.value = viewState.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}
