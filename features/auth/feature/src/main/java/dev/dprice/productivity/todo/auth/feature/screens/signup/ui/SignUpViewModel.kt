package dev.dprice.productivity.todo.auth.feature.screens.signup.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.model.SignUp
import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpEvent
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpForm
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpState
import dev.dprice.productivity.todo.auth.feature.usecase.UpdateEmailEntryUseCase
import dev.dprice.productivity.todo.auth.feature.usecase.UpdatePasswordEntryUseCase
import dev.dprice.productivity.todo.auth.feature.usecase.UpdateUsernameEntryUseCase
import dev.dprice.productivity.todo.auth.usecases.SignUpUserUseCase
import dev.dprice.productivity.todo.features.auth.feature.R
import dev.dprice.productivity.todo.ui.components.ButtonState
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SignUpViewModel {
    val viewState: SignUpState

    fun updateEntry(event: SignUpEvent)

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

    override fun updateEntry(event: SignUpEvent) {
        val updatedForm = when (event) {
            is SignUpEvent.UpdateEmailFocus -> viewState.form.copy(
                email = updateEmailEntryUseCase(viewState.form.email, event.focus)
            )
            is SignUpEvent.UpdateEmailValue -> viewState.form.copy(
                email = updateEmailEntryUseCase(viewState.form.email, event.value)
            )
            is SignUpEvent.UpdatePasswordFocus -> viewState.form.copy(
                password = updatePasswordEntryUseCase(viewState.form.password, event.focus)
            )
            is SignUpEvent.UpdatePasswordValue -> viewState.form.copy(
                password = updatePasswordEntryUseCase(viewState.form.password, event.value)
            )
            is SignUpEvent.UpdateUsernameFocus -> viewState.form.copy(
                username = updateUsernameEntryUseCase(viewState.form.username, event.focus)
            )
            is SignUpEvent.UpdateUsernameValue -> viewState.form.copy(
                username = updateUsernameEntryUseCase(viewState.form.username, event.value)
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
                        errorState = ErrorState.Message(R.string.error_unknown_error)
                    )
                }
                is SignUp.UsernameExists -> {
                    viewModelState.value = viewState.copy(
                        buttonState = ButtonState.ENABLED,
                        errorState = ErrorState.Message(R.string.error_username_exists)
                    )
                }
                else -> { /*todo: fill out other options */ }
            }
        }
    }

    private val SignUpForm.isValid
        get() = email.isValid && username.isValid && password.isValid
}
