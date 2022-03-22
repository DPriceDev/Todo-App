package dev.dprice.productivity.todo.auth.feature.ui.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpAction
import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpAction.*
import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpForm
import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpState
import dev.dprice.productivity.todo.auth.feature.ui.components.TitleBlock
import dev.dprice.productivity.todo.ui.components.RoundedButton
import dev.dprice.productivity.todo.ui.components.RoundedEntryCard
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun SignUp(
    wavyScaffoldState: WavyScaffoldState,
    offset: Float,
    position: Dp,
    frequency: Float,
    waveHeight: Dp,
    viewModel: SignUpViewModel = hiltViewModel<SignUpViewModelImpl>()
) {
    LaunchedEffect(key1 = wavyScaffoldState.targetPosition) {
        wavyScaffoldState.targetPosition.value = 128.dp
        wavyScaffoldState.targetFrequency.value = 0.3f
        wavyScaffoldState.targetHeight.value = 128.dp
        wavyScaffoldState.waveDuration.value = 15_000
    }

    WavyBackdropScaffold(
        backRevealHeight = position,
        waveHeight = waveHeight,
        waveFrequency = frequency,
        waveOffsetPercent = offset,
        backContent = {
            TitleBlock(colour = MaterialTheme.colors.background)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleBlock(colour = MaterialTheme.colors.primary)
            Text(
                text = "Sign up for a free account today!",
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Form(
                signUpForm = viewModel.viewState.form,
                canSubmit = viewModel.viewState.canSubmit,
                onEntryChanged = viewModel::onFormChanged,
                onSignInClicked = viewModel::goToSignIn,
                onSubmitForm = viewModel::submitForm
            )
        }
    }
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
            .padding(32.dp)
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
            enabled = canSubmit,
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

    override fun submitForm() {
        /* Stub */
    }

    override fun goToSignIn() {
        /* Stub */
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSignUp() {
    val state = WavyScaffoldState(
        targetPosition = remember{ mutableStateOf(128.dp) },
        targetHeight = remember{ mutableStateOf(128.dp) }
    )

    TodoAppTheme {
        SignUp(
            state,
            0f,
            128.dp,
            0.3f,
            128.dp,
            previewViewModel
        )
    }
}