package dev.dprice.productivity.todo.auth.feature.screens.signup.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.model.SignUp
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.ErrorState
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpAction
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpForm
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpState
import dev.dprice.productivity.todo.auth.usecases.auth.SignUpUserUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdateEmailEntryUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdatePasswordEntryUseCase
import dev.dprice.productivity.todo.auth.usecases.updater.UpdateUsernameEntryUseCase
import dev.dprice.productivity.todo.ui.components.ButtonState
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SignUpViewModel {
    val viewState: SignUpState

    fun onFormChanged(action: SignUpAction)

    fun submitForm(
        goToVerifyCode: (String) -> Unit,
        goToMainApp: () -> Unit
    )
}

@HiltViewModel
class SignUpViewModelImpl @Inject constructor(
    private val updateUsernameEntryUseCase: UpdateUsernameEntryUseCase,
    private val updateEmailEntryUseCase: UpdateEmailEntryUseCase,
    private val updatePasswordEntryUseCase: UpdatePasswordEntryUseCase,
    private val signUpUserUseCase: SignUpUserUseCase
) : ViewModel(),
    SignUpViewModel {

    private val viewModelState: MutableState<SignUpState> = mutableStateOf(
        SignUpState()
    )
    override val viewState: SignUpState by viewModelState

    override fun onFormChanged(action: SignUpAction) {
        val updatedForm = when (action.type) {
            SignUpAction.Type.UPDATE_EMAIL -> viewState.form.copy(
                email = updateEmailEntryUseCase(
                    viewState.form.email,
                    action.value,
                    action.focus
                )
            )
            SignUpAction.Type.UPDATE_USERNAME -> viewState.form.copy(
                username = updateUsernameEntryUseCase(
                    viewState.form.username,
                    action.value,
                    action.focus
                )
            )
            SignUpAction.Type.UPDATE_PASSWORD -> viewState.form.copy(
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
        goToVerifyCode: (String) -> Unit,
        goToMainApp: () -> Unit
    ) {
        viewModelState.value = viewState.copy(buttonState = ButtonState.LOADING)

        viewModelScope.launch {
            val response = signUpUserUseCase.invoke(
                viewState.form.username.value,
                viewState.form.email.value,
                viewState.form.password.value,
            )

            when (response) {
                is SignUp.Code -> goToVerifyCode(response.username)
                SignUp.Done -> goToMainApp()
                is SignUp.Error -> {
                    viewModelState.value = viewState.copy(
                        buttonState = ButtonState.ENABLED,
                        error = ErrorState.Message("Error Test!")
                    )
                }
                is SignUp.UsernameExists -> {
                    viewModelState.value = viewState.copy(
                        buttonState = ButtonState.ENABLED,
                        error = ErrorState.Message("Existing user error!")
                    )
                }
            }
        }
    }

    private val SignUpForm.isValid
        get() = email.isValid && username.isValid && password.isValid
}
