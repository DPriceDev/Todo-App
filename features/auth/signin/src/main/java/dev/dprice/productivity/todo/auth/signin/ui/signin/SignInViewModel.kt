package dev.dprice.productivity.todo.auth.signin.ui.signin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.model.SignInResponse
import dev.dprice.productivity.todo.auth.usecases.SignInUserUseCase
import dev.dprice.productivity.todo.auth.signin.model.ErrorState
import dev.dprice.productivity.todo.auth.signin.model.SignInAction
import dev.dprice.productivity.todo.auth.signin.model.SignInForm
import dev.dprice.productivity.todo.auth.signin.model.SignInState
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
    private val signInFormUpdater: SignInFormUpdater,
    private val signInUserUseCase: SignInUserUseCase
) : ViewModel(),
    SignInViewModel {

    private val mutableViewState: MutableState<SignInState> = mutableStateOf(SignInState())
    override val viewState: SignInState by mutableViewState

    override fun onFormChanged(action: SignInAction) {
        val signInForm = signInFormUpdater.updateEntry(viewState.form, action)
        mutableViewState.value = viewState.copy(
            form = signInForm,
            buttonState = if (signInForm.isValid) {
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
        mutableViewState.value = viewState.copy(buttonState = ButtonState.LOADING)

        viewModelScope.launch {
            val response = signInUserUseCase(
                viewState.form.username.value,
                viewState.form.password.value,
            )

            when (response) {
                is SignInResponse.Code -> goToVerifyCode(response.username)
                SignInResponse.Done -> goToMainApp()
                is SignInResponse.Error -> {
                    mutableViewState.value = viewState.copy(
                        buttonState = ButtonState.ENABLED,
                        error = ErrorState.Message("Error Test!")
                    )
                }
                is SignInResponse.AccountDisabled -> {
                    mutableViewState.value = viewState.copy(
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
