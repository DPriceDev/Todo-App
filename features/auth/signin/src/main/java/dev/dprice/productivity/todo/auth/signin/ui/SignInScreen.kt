package dev.dprice.productivity.todo.auth.signin.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.signin.viewmodel.SignInViewModel
import dev.dprice.productivity.todo.auth.signin.viewmodel.SignInViewModelImpl

@Composable
fun SignInTopContent() {

}

@Composable
fun SignInBottomContent(
    viewModel: SignInViewModel = hiltViewModel<SignInViewModelImpl>()
) {

}