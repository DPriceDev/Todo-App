package dev.dprice.productivity.todo.auth.signup.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.library.model.SignUpResponse
import dev.dprice.productivity.todo.auth.library.usecase.SignUpUserUseCase
import dev.dprice.productivity.todo.auth.signup.model.SignUpAction
import dev.dprice.productivity.todo.auth.signup.model.SignUpForm
import dev.dprice.productivity.todo.auth.signup.model.SignUpState
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SignUpViewModel {
    val viewState: SignUpState

    fun onFormChanged(action: SignUpAction)

    fun submitForm(
        goToVerifyCode: () -> Unit
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
            canSubmit = signUpForm.isValid
        )
    }

    override fun submitForm(
        goToVerifyCode: () -> Unit
    ) {
        // disable button
        viewModelScope.launch {
            val response = signUpUserUseCase.invoke(
                viewState.form.username.value,
                viewState.form.email.value,
                viewState.form.password.value,
            )

            when(response) {
                is SignUpResponse.Code -> goToVerifyCode()
                SignUpResponse.Done -> TODO() // navigate to app
                is SignUpResponse.Error -> TODO() // show error
                is SignUpResponse.UserExists -> TODO() // show user already exists error todo: extra information for email or username?
            }

            // re-enable button
        }
    }

    private val SignUpForm.isValid
        get() = email.isValid && username.isValid && password.isValid
}