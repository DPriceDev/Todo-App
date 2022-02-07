package dev.dprice.productivity.todo.auth.feature.ui.signup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpAction
import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpState
import dev.dprice.productivity.todo.auth.library.model.SignUpResponse
import dev.dprice.productivity.todo.auth.library.usecase.SignUpUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SignUpViewModel {
    val viewState: SignUpState

    fun onFormChanged(action: SignUpAction)

    fun submitForm()

    fun goToSignIn()
}

@HiltViewModel
class SignUpViewModelImpl @Inject constructor(
    private val signUpCredentialsUpdater: SignUpFormUpdater,
    private val signUpUserUseCase: SignUpUserUseCase
) : ViewModel(), SignUpViewModel {

    private val mutableViewState: MutableState<SignUpState> = mutableStateOf(SignUpState())
    override val viewState: SignUpState by mutableViewState

    override fun onFormChanged(action: SignUpAction) {
        mutableViewState.value = viewState.copy(
            form = signUpCredentialsUpdater.updateEntry(viewState.form, action)
        )
    }

    override fun goToSignIn() {
        // TODO("Not yet implemented") // navigate to verify
    }

    override fun submitForm() {
        // disable button
        viewModelScope.launch {
            val response = signUpUserUseCase.invoke(
                viewState.form.username.value,
                viewState.form.email.value,
                viewState.form.password.value,
            )

            when(response) {
                is SignUpResponse.Code -> TODO() // navigate to verify
                SignUpResponse.Done -> TODO() // navigate to app
                is SignUpResponse.Error -> TODO() // show error
                is SignUpResponse.UserExists -> TODO() // show user already exists error todo: extra information for email or username?
            }

            // re-enable button
        }
    }
}