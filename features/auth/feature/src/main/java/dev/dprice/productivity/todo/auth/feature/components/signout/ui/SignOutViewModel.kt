package dev.dprice.productivity.todo.auth.feature.components.signout.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.usecases.SignOutUserUseCase
import dev.dprice.productivity.todo.ui.components.buttons.ButtonState
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SignOutViewModel {
    val buttonState: ButtonState

    fun logOutUser()
}

@HiltViewModel
class SignOutViewModelImpl @Inject constructor(
    private val signOutUserUseCase: SignOutUserUseCase
) : ViewModel(),
    SignOutViewModel {

    private val viewModelButtonState: MutableState<ButtonState> = mutableStateOf(
        ButtonState.ENABLED
    )
    override val buttonState: ButtonState by viewModelButtonState

    override fun logOutUser() {
        viewModelButtonState.value = ButtonState.LOADING
        viewModelScope.launch {
            signOutUserUseCase()
        }
        viewModelButtonState.value = ButtonState.ENABLED
    }
}
