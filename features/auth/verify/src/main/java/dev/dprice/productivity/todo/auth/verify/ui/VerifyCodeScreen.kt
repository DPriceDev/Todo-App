package dev.dprice.productivity.todo.auth.verify.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.verify.viewmodel.VerifyCodeViewModel
import dev.dprice.productivity.todo.auth.verify.viewmodel.VerifyCodeViewModelImpl
import dev.dprice.productivity.todo.ui.components.TitleBlock
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

@Composable
fun VerifyCodeTopContent() {
    TitleBlock(colour = MaterialTheme.colors.background)
}

@Composable
fun VerifyCodeBottomContent(
    viewModel: VerifyCodeViewModel = hiltViewModel<VerifyCodeViewModelImpl>()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBlock(colour = MaterialTheme.colors.primary, title = "Verify Sign Up")
    }
}

@Composable
fun VerifyCode(
    state: WavyScaffoldState,
    viewModel: VerifyCodeViewModel = hiltViewModel<VerifyCodeViewModelImpl>()
) {
    val ini = rememberInfiniteTransition()
    val test = ini.animateFloat(initialValue = 0.0f, targetValue = 1.0f, animationSpec = infiniteRepeatable(
        tween(300)
    ))

    WavyBackdropScaffold(
        state = state,
        phaseOffset = 0.3f,
        backContent = {
            TitleBlock(colour = MaterialTheme.colors.background, title = "Verify Sign Up")
        },
        frontContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(colour = MaterialTheme.colors.primary, title = "Verify Sign Up")
                Text(text = "Content")
            }
        }
    )
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewVerifyCode() {

}