package dev.dprice.productivity.todo.auth.feature.screens.resetpassword.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.model.ResetPassword
import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model.ResetPasswordEvent
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model.ResetPasswordForm
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model.ResetPasswordState
import dev.dprice.productivity.todo.auth.usecases.ResetPasswordUseCase
import dev.dprice.productivity.todo.auth.feature.usecase.UpdateCodeEntryUseCase
import dev.dprice.productivity.todo.auth.feature.usecase.UpdatePasswordEntryUseCase
import dev.dprice.productivity.todo.features.auth.feature.R
import dev.dprice.productivity.todo.ui.components.buttons.ButtonState
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ResetPasswordViewModel {
    val viewState: ResetPasswordState

    fun update(event: ResetPasswordEvent)

    fun submit(goBackToSignIn: () -> Unit)
}

@HiltViewModel
class ResetPasswordViewModelImpl @Inject constructor(
    private val updatePasswordEntryUseCase: UpdatePasswordEntryUseCase,
    private val updateCodeEntryUseCase: UpdateCodeEntryUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel(),
    ResetPasswordViewModel {

    private val viewModelState: MutableState<ResetPasswordState> = mutableStateOf(ResetPasswordState())
    override val viewState: ResetPasswordState by viewModelState

    override fun update(event: ResetPasswordEvent) {
        val updatedForm = when (event) {
            is ResetPasswordEvent.UpdatePasswordValue -> viewState.form.copy(
                password = updatePasswordEntryUseCase(viewState.form.password, event.value)
            )
            is ResetPasswordEvent.UpdatePasswordFocus -> viewState.form.copy(
                password = updatePasswordEntryUseCase(viewState.form.password, event.focus)
            )
            is ResetPasswordEvent.UpdateCodeValue -> viewState.form.copy(
                code = updateCodeEntryUseCase(viewState.form.code, event.value)
            )
            is ResetPasswordEvent.UpdateCodeFocus -> viewState.form.copy(
                code = updateCodeEntryUseCase(viewState.form.code, event.focus)
            )
        }

        viewModelState.value = viewState.copy(
            form = updatedForm,
            buttonState = when (viewState.buttonState) {
                ButtonState.LOADING -> ButtonState.LOADING
                else -> if (updatedForm.isValid) ButtonState.ENABLED else ButtonState.DISABLED
            }
        )
    }

    override fun submit(goBackToSignIn: () -> Unit) {
        if (!viewState.form.isValid || viewState.buttonState == ButtonState.LOADING) return

        viewModelState.value = viewState.copy(
            buttonState = ButtonState.LOADING
        )

        viewModelScope.launch {
            val response = resetPasswordUseCase(
                code = viewState.form.code.value,
                newPassword = viewState.form.password.value
            )

            // todo: either send toast or animate spinner into tick
            // go to verify code and new password screen
            when (response) {
                ResetPassword.Done -> goBackToSignIn()
                is ResetPassword.Error -> {
                    viewModelState.value = viewState.copy(
                        errorState = ErrorState.Message(R.string.error_unknown_error),
                        buttonState = ButtonState.ENABLED
                    )
                }
                ResetPassword.ConnectionError -> {
                    viewModelState.value = viewState.copy(
                        errorState = ErrorState.Message(R.string.error_no_internet),
                        buttonState = ButtonState.ENABLED
                    )
                }
            }
        }
    }

    private val ResetPasswordForm.isValid
        get() = code.isValid && password.isValid
}
