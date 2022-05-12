package dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.model.ForgotPassword
import dev.dprice.productivity.todo.auth.usecases.auth.SendForgotPasswordUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdateUsernameEntryUseCase
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ErrorState {
    object None : ErrorState()
    data class Error(val message: String) : ErrorState()
}

data class ForgotPasswordState(
    val username: EntryField = EntryField(
        icon = Icons.Outlined.Person,
        contentDescription = "Username",
        hintText = "Username",
        errorText = "Please enter a valid username."
    ),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: ErrorState = ErrorState.None
)

interface ForgotPasswordViewModel {
    val viewState: ForgotPasswordState

    fun updateUsername(username: String, focus: Boolean)

    fun submit(goToResetPassword: () -> Unit)
}

@HiltViewModel
class ForgotPasswordViewModelImpl @Inject constructor(
    private val updateUsernameEntryUseCase: UpdateUsernameEntryUseCase,
    private val sendForgotPasswordUseCase: SendForgotPasswordUseCase
) : ViewModel(), ForgotPasswordViewModel {

    private val viewModelState: MutableState<ForgotPasswordState> = mutableStateOf(ForgotPasswordState())
    override val viewState: ForgotPasswordState by viewModelState

    override fun updateUsername(username: String, focus: Boolean) {
        val updatedUsername = updateUsernameEntryUseCase(viewState.username, username, focus)
        viewModelState.value = viewState.copy(
            username = updatedUsername,
            buttonState = if (updatedUsername.isValid) ButtonState.ENABLED else ButtonState.DISABLED
        )
    }

    override fun submit(goToResetPassword: () -> Unit) {
        viewModelState.value = viewState.copy(buttonState = ButtonState.LOADING)

        viewModelScope.launch {
            val response = sendForgotPasswordUseCase.invoke(
                username = viewState.username.value
            )

            // todo: either send toast or animate spinner into tick
            // go to verify code and new password screen
            when (response) {
                ForgotPassword.Done -> goToResetPassword()
                is ForgotPassword.Error -> {
                    viewModelState.value = viewState.copy(
                        errorState = ErrorState.Error("error!"),
                        buttonState = ButtonState.ENABLED
                    )
                }
                ForgotPassword.ConnectionError -> TODO()
            }
        }
    }
}
