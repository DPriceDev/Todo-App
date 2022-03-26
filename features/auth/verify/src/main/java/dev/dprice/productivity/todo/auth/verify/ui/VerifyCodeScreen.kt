package dev.dprice.productivity.todo.auth.verify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.verify.viewmodel.VerifyCodeViewModel
import dev.dprice.productivity.todo.auth.verify.viewmodel.VerifyCodeViewModelImpl
import dev.dprice.productivity.todo.ui.components.*
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun VerifyCode(
    state: WavyScaffoldState,
    viewModel: VerifyCodeViewModel = hiltViewModel<VerifyCodeViewModelImpl>()
) {
    WavyBackdropScaffold(
        state = state,
        phaseOffset = 0.3f,
        backContent = {
            TitleBlock(colour = MaterialTheme.colors.background, title = "Verify Sign Up")
        },
        frontContent = {
            val focusManager = LocalFocusManager.current

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(colour = MaterialTheme.colors.primary, title = "Verify Sign Up")

                Text(
                    text = "Please enter the verification code sent to your email",
                    modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                )

                CodeEntry()

                // todo: 6 squares
                // circle around focused
                // move focus to each one selected

                RoundedButton(
                    onClick = viewModel::onSubmit,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                    contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
                    enabled = viewModel.canSubmit,
                ) {
                    Text(text = "Verify")
                }

                TextWithClickableSuffix(
                    text = "",
                    suffixText = "Resend verification code",
                    onClick = { /* todo: on click */ }
                )

            }
        }
    )
}

@Composable
fun CodeEntry() {

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp)
    ) {
        repeat(2) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                repeat(3) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "6",
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewVerifyCode() {
    val state = WavyScaffoldState(

    )
    TodoAppTheme() {
        VerifyCode(state = state)
    }
}