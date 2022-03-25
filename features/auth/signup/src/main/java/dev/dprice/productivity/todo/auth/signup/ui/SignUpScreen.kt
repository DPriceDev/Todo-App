package dev.dprice.productivity.todo.auth.signup.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.signup.model.SignUpAction
import dev.dprice.productivity.todo.auth.signup.model.SignUpAction.Type
import dev.dprice.productivity.todo.auth.signup.model.SignUpForm
import dev.dprice.productivity.todo.auth.signup.model.SignUpState
import dev.dprice.productivity.todo.auth.signup.viewmodel.SignUpViewModel
import dev.dprice.productivity.todo.auth.signup.viewmodel.SignUpViewModelImpl
import dev.dprice.productivity.todo.ui.components.*
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun SignUp(
    state: WavyScaffoldState,
    goToVerifyCode: () -> Unit,
    goToSignIn: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel<SignUpViewModelImpl>()
) {
    WavyBackdropScaffold(
        state = state,
        backContent = {
            TitleBlock(colour = MaterialTheme.colors.background)
        },
        frontContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(colour = MaterialTheme.colors.primary)
                Text(
                    text = "Sign up for a free account today!",
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                )
                Form(
                    signUpForm = viewModel.viewState.form,
                    canSubmit = viewModel.viewState.canSubmit,
                    onEntryChanged = viewModel::onFormChanged,
                    onSignInClicked = goToSignIn,
                    onSubmitForm = {
                        goToVerifyCode()
                        //viewModel.submitForm(goToVerifyCode)
                    }
                )
            }
        }
    )
}

@Composable
private fun Form(
    signUpForm: SignUpForm,
    canSubmit: Boolean,
    onEntryChanged: (SignUpAction) -> Unit,
    onSubmitForm: () -> Unit,
    onSignInClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedEntryCard(
            entry = signUpForm.email,
            modifier = Modifier.onFocusChanged {
                onEntryChanged(SignUpAction(signUpForm.email.value, it.hasFocus, Type.UPDATE_EMAIL))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = {
                onEntryChanged(SignUpAction(it, signUpForm.email.hasFocus, Type.UPDATE_EMAIL))
            }
        )
        RoundedEntryCard(
            entry = signUpForm.username,
            modifier = Modifier.onFocusChanged {
                onEntryChanged(SignUpAction(signUpForm.username.value, it.hasFocus, Type.UPDATE_USERNAME))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = {
                onEntryChanged(SignUpAction(it, signUpForm.username.hasFocus, Type.UPDATE_USERNAME))
            }
        )
        RoundedEntryCard(
            entry = signUpForm.password,
            modifier = Modifier.onFocusChanged {
                onEntryChanged(
                    SignUpAction(signUpForm.password.value, it.hasFocus, Type.UPDATE_PASSWORD)
                )
            },
            onImeAction = { focusManager.clearFocus() },
            onTextChanged = {
                onEntryChanged(
                    SignUpAction(it, signUpForm.password.hasFocus, Type.UPDATE_PASSWORD)
                )
            }
        )

        RoundedButton(
            onClick = onSubmitForm,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
            contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
            enabled = true,
        ) {
            Text(text = "Create Account")
        }

        SignInText(onSignInClicked)
    }
}

@Composable
private fun SignInText(onSignInClicked: () -> Unit) {
    val submitText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colors.onBackground)) {
            append("Already have an account? ")
        }

        pushStringAnnotation(tag = "signIn", annotation = "signIn")
        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
            append("Sign In")
        }
    }

    ClickableText(
        text = submitText,
        style = MaterialTheme.typography.body1,
        onClick = { offset ->
            submitText.getStringAnnotations("signIn", offset, offset)
                .firstOrNull()
                ?.let {
                    onSignInClicked()
                }
        }
    )
}

/**
 * Preview
 */

private val previewViewModel = object : SignUpViewModel {
    override val viewState: SignUpState = SignUpState()

    override fun onFormChanged(action: SignUpAction) {
        /* Stub */
    }

    override fun submitForm(goToVerifyCode: () -> Unit) {
        /* Stub */
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSignUp() {
    TodoAppTheme {
        val state = WavyScaffoldState()

        SignUp(
            state,
            { },
            { },
            previewViewModel
        )
    }
}