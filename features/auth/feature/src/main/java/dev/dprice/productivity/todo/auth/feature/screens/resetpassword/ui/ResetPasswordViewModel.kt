package dev.dprice.productivity.todo.auth.feature.screens.resetpassword.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.model.ResetPassword
import dev.dprice.productivity.todo.auth.usecases.auth.ResetPasswordUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdateEntryUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdatePasswordEntryUseCase
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.transforms.DashedEntryVisualTransformation
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ErrorState {
    object None : ErrorState()
    data class Error(val message: String) : ErrorState()
}

data class ResetPasswordForm(
    val code: EntryField = EntryField(
        contentDescription = "Reset Code",
        errorText = "Please enter your 6 digit reset code",
        maxLength = 6,
        visualTransformation = DashedEntryVisualTransformation(6)
    ),
    val password: EntryField = EntryField(
        icon = Icons.Outlined.Password,
        contentDescription = "New Password",
        hintText = "New Password",
        errorText = "Please enter a valid password.",
        visualTransformation = PasswordVisualTransformation(),
        imeAction = ImeAction.Done
    ),
)

data class ResetPasswordState(
    val form: ResetPasswordForm = ResetPasswordForm(),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: ErrorState = ErrorState.None
)

interface ResetPasswordViewModel {
    val viewState: ResetPasswordState

    fun update(event: ResetPasswordEvent)

    fun submit(goBackToSignIn: () -> Unit)
}

@HiltViewModel
class ResetPasswordViewModelImpl @Inject constructor(
    private val updatePasswordEntryUseCase: UpdatePasswordEntryUseCase,
    private val updateCodeEntryUseCase: UpdateEntryUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel(),
    ResetPasswordViewModel {

    private val viewModelState: MutableState<ResetPasswordState> = mutableStateOf(ResetPasswordState())
    override val viewState: ResetPasswordState by viewModelState

    override fun update(event: ResetPasswordEvent) {
        val updatedForm = when (event) {
            is ResetPasswordEvent.UpdatePasswordValue -> viewState.form.copy(
                password = updatePasswordEntryUseCase(
                    viewState.form.password,
                    event.value,
                    viewState.form.password.hasFocus
                )
            )
            is ResetPasswordEvent.UpdatePasswordFocus -> viewState.form.copy(
                password = updateCodeEntryUseCase(
                    viewState.form.password,
                    viewState.form.password.value,
                    event.focus
                )
            )
            is ResetPasswordEvent.UpdateCodeValue -> viewState.form.copy(
                code = updateCodeEntryUseCase(
                    viewState.form.code,
                    event.value,
                    viewState.form.code.hasFocus
                )
            )
            is ResetPasswordEvent.UpdateCodeFocus -> viewState.form.copy(
                code = updateCodeEntryUseCase(
                    viewState.form.code,
                    viewState.form.code.value,
                    event.focus
                )
            )
        }

        viewModelState.value = viewState.copy(
            form = updatedForm,
            buttonState = if (updatedForm.isValid) ButtonState.ENABLED else ButtonState.DISABLED
        )
    }

    override fun submit(goBackToSignIn: () -> Unit) {
        if (!viewState.form.isValid) return // todo: maybe show error?

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
                        errorState = ErrorState.Error("error!"),
                        buttonState = ButtonState.ENABLED
                    )
                }
                ResetPassword.ConnectionError -> TODO()
            }
        }
    }

    private val ResetPasswordForm.isValid
        get() = code.isValid && password.isValid
}
