package dev.dprice.productivity.todo.auth.signup.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.library.model.SignUpResponse
import dev.dprice.productivity.todo.auth.library.usecase.SignUpUserUseCase
import dev.dprice.productivity.todo.auth.signup.model.ErrorState
import dev.dprice.productivity.todo.auth.signup.model.SignUpAction
import dev.dprice.productivity.todo.auth.signup.model.SignUpForm
import dev.dprice.productivity.todo.auth.signup.model.SignUpState
import dev.dprice.productivity.todo.ui.components.ButtonEnablement
import kotlinx.coroutines.launch
import timber.log.Timber
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
    private val signUpFormUpdater: SignUpFormUpdater,
    private val signUpUserUseCase: SignUpUserUseCase
) : ViewModel(), SignUpViewModel {

    private val mutableViewState: MutableState<SignUpState> = mutableStateOf(SignUpState())
    override val viewState: SignUpState by mutableViewState

    override fun onFormChanged(action: SignUpAction) {
        val signUpForm = signUpFormUpdater.updateEntry(viewState.form, action)
        mutableViewState.value = viewState.copy(
            form = signUpForm,
            buttonEnablement = if(signUpForm.isValid) {
                ButtonEnablement.ENABLED
            } else {
                ButtonEnablement.DISABLED
            }
        )
    }

    override fun submitForm(
        goToVerifyCode: (String) -> Unit,
        goToMainApp: () -> Unit
    ) {
        mutableViewState.value = viewState.copy(buttonEnablement = ButtonEnablement.LOADING)

        Timber.tag("Sign Up ViewModel").d(
            "user: ${ viewState.form.username.value } email: ${ viewState.form.email.value } pass: ${ viewState.form.password.value }"
        )

        viewModelScope.launch {
            val response = signUpUserUseCase.invoke(
                viewState.form.username.value,
                viewState.form.email.value,
                viewState.form.password.value,
            )

            when(response) {
                is SignUpResponse.Code -> goToVerifyCode(response.username)
                SignUpResponse.Done -> goToMainApp()
                is SignUpResponse.Error -> {
                    mutableViewState.value = viewState.copy(
                        buttonEnablement =  ButtonEnablement.ENABLED,
                        error = ErrorState.Message("Error Test!")
                    )
                }
                is SignUpResponse.UserExists -> {
                    mutableViewState.value = viewState.copy(
                        buttonEnablement =  ButtonEnablement.ENABLED,
                        error = ErrorState.Message("Existing user error!")
                    )
                }
            }
        }
    }

    private val SignUpForm.isValid
        get() = email.isValid && username.isValid && password.isValid
}