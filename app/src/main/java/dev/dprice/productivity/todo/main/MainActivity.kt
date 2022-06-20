package dev.dprice.productivity.todo.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.dprice.productivity.todo.main.ui.MainScreen
import dev.dprice.productivity.todo.main.ui.MainViewModel
import dev.dprice.productivity.todo.main.ui.MainViewModelImpl
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.viewState.isLoading
        }
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                MainScreen(state = viewModel.viewState)
            }
        }
    }
}
