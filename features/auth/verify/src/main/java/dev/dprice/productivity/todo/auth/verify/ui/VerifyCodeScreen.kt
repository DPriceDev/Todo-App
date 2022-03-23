package dev.dprice.productivity.todo.auth.verify.ui

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
        TitleBlock(colour = MaterialTheme.colors.primary)
        Text(text = "Verify Sign Up")
    }
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewVerifyCode() {

}