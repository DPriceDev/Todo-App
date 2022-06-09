package dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.model.ForgotPassword
import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.model.ForgotPasswordEvent
import dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.model.ForgotPasswordState
import dev.dprice.productivity.todo.auth.usecases.auth.SendForgotPasswordUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdateUsernameEntryUseCase
import dev.dprice.productivity.todo.features.auth.feature.R
import dev.dprice.productivity.todo.ui.components.ButtonState
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ForgotPasswordViewModel {
    val viewState: ForgotPasswordState

    fun updateEntry(event: ForgotPasswordEvent)

    fun submit(goToResetPassword: () -> Unit)
}

@HiltViewModel
class ForgotPasswordViewModelImpl @Inject constructor(
    private val updateUsernameEntryUseCase: UpdateUsernameEntryUseCase,
    private val sendForgotPasswordUseCase: SendForgotPasswordUseCase
) : ViewModel(),
    ForgotPasswordViewModel {

    private val viewModelState: MutableState<ForgotPasswordState> = mutableStateOf(ForgotPasswordState())
    override val viewState: ForgotPasswordState by viewModelState

    override fun updateEntry(event: ForgotPasswordEvent) {
        val updatedUsername = when (event) {
            is ForgotPasswordEvent.UpdateUsernameFocus -> updateUsernameEntryUseCase(
                viewState.username,
                event.focus
            )
            is ForgotPasswordEvent.UpdateUsernameValue -> updateUsernameEntryUseCase(
                viewState.username,
                event.value
            )
        }
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
                        errorState = ErrorState.Message(R.string.error_unknown_error),
                        buttonState = ButtonState.ENABLED
                    )
                }
                ForgotPassword.ConnectionError -> {
                    viewModelState.value = viewState.copy(
                        errorState = ErrorState.Message(R.string.error_no_internet),
                        buttonState = ButtonState.ENABLED
                    )
                }
            }
        }
    }
}
