package dev.dprice.productivity.todo.auth.feature.ui.signin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.feature.model.signin.SignInAction
import dev.dprice.productivity.todo.auth.feature.model.signin.SignInState
import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpAction
import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpState
import javax.inject.Inject

interface SignInViewModel {
    val viewState: SignInState

    fun onFormChanged(action: SignInAction)

    fun submitForm()

    fun goToSignUp()
}

@HiltViewModel
class SignInViewModelImpl @Inject constructor() : ViewModel(), SignInViewModel {

    private val mutableViewState: MutableState<SignInState> = mutableStateOf(SignInState())
    override val viewState: SignInState by mutableViewState

    override fun onFormChanged(action: SignInAction) {
        TODO("Not yet implemented")
    }

    override fun submitForm() {
        TODO("Not yet implemented")
    }

    override fun goToSignUp() {
        TODO("Not yet implemented")
    }

}