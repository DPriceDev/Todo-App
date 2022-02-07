package dev.dprice.productivity.todo.auth.ui.signup

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.model.SignUpResponse
import dev.dprice.productivity.todo.auth.usecase.SignUpUserUseCase
import dev.dprice.productivity.todo.ui.components.EntryField
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpForm(
    val email: EntryField = EntryField(
        icon = Icons.Outlined.Email,
        contentDescription = "Email",
        hintText = "Email",
        errorText = "Please enter a valid email."
    ),
    val username: EntryField = EntryField(
        icon = Icons.Outlined.Person,
        contentDescription = "Username",
        hintText = "Username",
        errorText = "Please enter a valid username."
    ),
    val password: EntryField = EntryField(
        icon = Icons.Outlined.Password,
        contentDescription = "Password",
        hintText = "Password",
        errorText = "Please enter a valid password.",
        visualTransformation = PasswordVisualTransformation(),
        imeAction = ImeAction.Done
    )
)

data class SignUpState(
    val form: SignUpForm = SignUpForm()
)

data class SignUpAction(
    val value: String,
    val focus: Boolean,
    val type: Type
) {
    enum class Type {
        UPDATE_EMAIL,
        UPDATE_USERNAME,
        UPDATE_PASSWORD
    }
}

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
            when(signUpUserUseCase.invoke(viewState.form)) {
                is SignUpResponse.Code -> TODO() // navigate to verify
                SignUpResponse.Done -> TODO() // navigate to app
                is SignUpResponse.Error -> TODO() // show error
                is SignUpResponse.UserExists -> TODO() // show user already exists error todo: extra information for email or username?
            }

            // re-enable button
        }
    }
}