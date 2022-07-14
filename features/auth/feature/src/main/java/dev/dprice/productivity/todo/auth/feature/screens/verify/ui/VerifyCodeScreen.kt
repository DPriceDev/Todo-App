package dev.dprice.productivity.todo.auth.feature.screens.verify.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.auth.feature.screens.verify.model.VerifyCodeEvent
import dev.dprice.productivity.todo.auth.feature.screens.verify.model.VerifyCodeEvent.UpdateCodeFocus
import dev.dprice.productivity.todo.auth.feature.screens.verify.model.VerifyCodeEvent.UpdateCodeValue
import dev.dprice.productivity.todo.auth.feature.screens.verify.model.VerifyState
import dev.dprice.productivity.todo.auth.feature.ui.AuthWavyScaffold
import dev.dprice.productivity.todo.features.auth.feature.R
import dev.dprice.productivity.todo.ui.components.TextWithClickableSuffix
import dev.dprice.productivity.todo.ui.components.buttons.RoundedButton
import dev.dprice.productivity.todo.ui.components.dialogs.AppDialog
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldStateProvider
import dev.dprice.productivity.todo.ui.components.text.RoundedEntryCard
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun VerifyCode(
    state: VerifyState,
    wavyScaffoldState: WavyScaffoldState,
    username: String,
    updateCode: (VerifyCodeEvent) -> Unit,
    onSubmit: (String) -> Unit,
    onResendClicked: (String) -> Unit,
    goBack: () -> Unit
) {
    OnBackDialog(onConfirm = goBack)

    AuthWavyScaffold(
        state = wavyScaffoldState,
        title = stringResource(id = R.string.verify_account),
        description = stringResource(id = R.string.enter_code_question),
        phaseOffset = 0.3f,
        frontContent = {
            val focusManager = LocalFocusManager.current

            RoundedEntryCard(
                entry = state.code,
                textStyle = MaterialTheme.typography.h2.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(top = 24.dp)
                    .width(256.dp)
                    .onFocusChanged {
                        updateCode(UpdateCodeFocus(it.hasFocus))
                    },
                onImeAction = {
                    focusManager.clearFocus()
                    onSubmit(username)
                },
                onTextChanged = {
                    updateCode(UpdateCodeValue(it))
                }
            )

            RoundedButton(
                text = stringResource(id = R.string.verify_button),
                onClick = {
                    onSubmit(username)
                },
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
                buttonState = state.buttonState,
            )

            Text(
                text = stringResource(id = R.string.not_received_code_question),
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )

            TextWithClickableSuffix(
                text = "",
                suffixText = stringResource(id = R.string.resend_code_question),
                onClick = {
                    onResendClicked(username)
                }
            )
        }
    )
}

@Composable
fun OnBackDialog(
    onConfirm: () -> Unit
) {
    val isDialogShown: MutableState<Boolean> = remember { mutableStateOf(false) }

    BackHandler(enabled = !isDialogShown.value) {
        isDialogShown.value = true
    }

    if (isDialogShown.value) {
        AppDialog(
            title = stringResource(id = R.string.back_dialog_title),
            message = stringResource(id = R.string.back_dialog_description),
            onConfirm = onConfirm,
            isDialogShown = isDialogShown
        )
    }
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewVerifyCode(
    @PreviewParameter(WavyScaffoldStateProvider::class) state: WavyScaffoldState
) {
    TodoAppTheme() {
        VerifyCode(
            state = VerifyState(),
            wavyScaffoldState = state,
            "testUser",
            { },
            { },
            { },
            { }
        )
    }
}
