package dev.dprice.productivity.todo.auth.signin.ui.forgot

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.library.model.ForgotPasswordResponse
import dev.dprice.productivity.todo.auth.usecases.SendForgotPasswordUseCase
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ErrorState {
    object None : ErrorState()
    data class Error(val message: String) : ErrorState()
}

data class ForgotPasswordState(
    val email: EntryField = EntryField(
        icon = Icons.Outlined.Email,
        contentDescription = "Email",
        hintText = "Email",
        errorText = "Please enter a valid email."
    ),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: ErrorState = ErrorState.None
)

interface ForgotPasswordViewModel {
    val viewState: ForgotPasswordState

    fun updateEmail(email: String, focus: Boolean)

    fun submit(goToResetPassword: () -> Unit)
}

@HiltViewModel
class ForgotPasswordViewModelImpl @Inject constructor(
    private val verifyUserEmailUpdater: VerifyEmailEntryUseCase,
    private val sendForgotPasswordUseCase: SendForgotPasswordUseCase
) : ViewModel(), ForgotPasswordViewModel {

    private val viewModelState: MutableState<ForgotPasswordState> = mutableStateOf(ForgotPasswordState())
    override val viewState: ForgotPasswordState by viewModelState

    override fun updateEmail(email: String, focus: Boolean) {
        val updatedEmail = verifyUserEmailUpdater(viewState.email, email, focus)
        viewModelState.value = viewState.copy(
            email = updatedEmail,
            buttonState = if (updatedEmail.isValid) ButtonState.ENABLED else ButtonState.DISABLED
        )
    }

    override fun submit(goToResetPassword: () -> Unit) {
        viewModelState.value = viewState.copy(buttonState = ButtonState.LOADING)

        viewModelScope.launch {
            val response = sendForgotPasswordUseCase.invoke(
                email = viewState.email.value
            )

            // todo: either send toast or animate spinner into tick
            // go to verify code and new password screen
            when (response) {
                ForgotPasswordResponse.Done -> goToResetPassword()
                is ForgotPasswordResponse.Error -> {
                    viewModelState.value = viewState.copy(
                        errorState = ErrorState.Error("error!"),
                        buttonState = ButtonState.ENABLED
                    )
                }
            }
        }
    }
}