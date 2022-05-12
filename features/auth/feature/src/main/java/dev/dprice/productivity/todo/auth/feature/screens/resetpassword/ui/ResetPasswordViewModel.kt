package dev.dprice.productivity.todo.auth.feature.screens.resetpassword.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.feature.ui.transformers.DashedEntryVisualTransformation
import dev.dprice.productivity.todo.auth.usecases.updater.UpdateEntryUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdatePasswordEntryUseCase
import dev.dprice.productivity.todo.ui.components.ButtonState
import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

sealed class ErrorState {
    object None : ErrorState()
    data class Error(val message: String) : ErrorState()
}

data class ResetPasswordState(
    val code: EntryField = EntryField(
        contentDescription = "Reset Code",
        errorText = "Please enter your 6 digit reset code",
        maxLength = 6,
        visualTransformation = DashedEntryVisualTransformation(6)
    ),
    val password: EntryField = EntryField(
        icon = Icons.Outlined.Password,
        contentDescription = "Password",
        hintText = "Password",
        errorText = "Please enter a valid password.",
        visualTransformation = PasswordVisualTransformation(),
        imeAction = ImeAction.Done
    ),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: ErrorState = ErrorState.None
)

interface ResetPasswordViewModel {
    val viewState: ResetPasswordState

    fun update(event: ResetPasswordEvent, value: String, focus: Boolean)

    fun submit(goBackToSignIn: () -> Unit)
}

@HiltViewModel
class ResetPasswordViewModelImpl @Inject constructor(
    private val updatePasswordEntryUseCase: UpdatePasswordEntryUseCase,
    private val updateCodeEntryUseCase: UpdateEntryUseCase
) : ViewModel(), ResetPasswordViewModel {

    private val viewModelState: MutableState<ResetPasswordState> = mutableStateOf(ResetPasswordState())
    override val viewState: ResetPasswordState by viewModelState

    override fun update(event: ResetPasswordEvent, value: String, focus: Boolean) {
        viewModelState.value = when (event) {
            ResetPasswordEvent.UPDATE_PASSWORD -> viewState.copy(
                password = updatePasswordEntryUseCase(viewState.password, value, focus)
            )
            ResetPasswordEvent.UPDATE_CODE -> viewState.copy(
                code = updateCodeEntryUseCase(viewState.code, value, focus)
            )
        }
    }

    override fun submit(goBackToSignIn: () -> Unit) {
        TODO("Not yet implemented")
    }
}
