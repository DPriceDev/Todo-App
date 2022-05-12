package dev.dprice.productivity.todo.auth.signin.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.model.SignIn
import dev.dprice.productivity.todo.auth.signin.model.ErrorState
import dev.dprice.productivity.todo.auth.signin.model.SignInAction
import dev.dprice.productivity.todo.auth.signin.model.SignInForm
import dev.dprice.productivity.todo.auth.signin.model.SignInState
import dev.dprice.productivity.todo.auth.usecases.auth.SignInUserUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdatePasswordEntryUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdateUsernameEntryUseCase
import dev.dprice.productivity.todo.ui.components.ButtonState
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SignInViewModel {
    val viewState: SignInState

    fun onFormChanged(action: SignInAction)

    fun submitForm(
        goToMainApp: () -> Unit,
        goToVerifyCode: (String) -> Unit
    )
}

@HiltViewModel
class SignInViewModelImpl @Inject constructor(
    private val updateUsernameEntryUseCase: UpdateUsernameEntryUseCase,
    private val updatePasswordEntryUseCase: UpdatePasswordEntryUseCase,
    private val signInUserUseCase: SignInUserUseCase
) : ViewModel(),
    SignInViewModel {

    private val viewModelState: MutableState<SignInState> = mutableStateOf(SignInState())
    override val viewState: SignInState by viewModelState

    override fun onFormChanged(action: SignInAction) {

        val updatedForm = when (action.type) {
            SignInAction.Type.UPDATE_USERNAME -> viewState.form.copy(
                username = updateUsernameEntryUseCase(
                    viewState.form.username,
                    action.value,
                    action.focus
                )
            )
            SignInAction.Type.UPDATE_PASSWORD -> viewState.form.copy(
                password = updatePasswordEntryUseCase(
                    viewState.form.password,
                    action.value,
                    action.focus
                )
            )
        }

        viewModelState.value = viewState.copy(
            form = updatedForm,
            buttonState = if (updatedForm.isValid) {
                ButtonState.ENABLED
            } else {
                ButtonState.DISABLED
            }
        )
    }

    override fun submitForm(
        goToMainApp: () -> Unit,
        goToVerifyCode: (String) -> Unit
    ) {
        if (!viewState.form.isValid) return
        viewModelState.value = viewState.copy(buttonState = ButtonState.LOADING)

        viewModelScope.launch {
            val response = signInUserUseCase(
                viewState.form.username.value,
                viewState.form.password.value,
            )

            when (response) {
                is SignIn.Code -> goToVerifyCode(response.username)
                SignIn.Done -> goToMainApp()
                is SignIn.Error -> {
                    viewModelState.value = viewState.copy(
                        buttonState = ButtonState.ENABLED,
                        error = ErrorState.Message("Error Test!")
                    )
                }
                is SignIn.AccountDisabled -> {
                    viewModelState.value = viewState.copy(
                        buttonState = ButtonState.ENABLED,
                        error = ErrorState.Message("Account Disabled")
                    )
                }
            }
        }
    }

    private val SignInForm.isValid
        get() = username.isValid && password.isValid
}
