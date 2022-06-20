package dev.dprice.productivity.todo.main.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dev.dprice.productivity.todo.main.AppNavigation
import dev.dprice.productivity.todo.main.model.MainState
import dev.dprice.productivity.todo.ui.shapes.waveToppedShape
import dev.dprice.productivity.todo.ui.theme.DarkBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainState,
    modifier: Modifier = Modifier
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    LaunchedEffect(key1 = state.userSession) {
        state.userSession?.let { session ->
            if (!session.isSignedIn) {
                navController.navigate("Auth")
            }
        }
    }

    val bottomSheetWaveOffset: Float by rememberInfiniteTransition().animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 17000)
        )
    )

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        scrimColor = Color.Black.copy(alpha = 0.32f),
        sheetShape = waveToppedShape(
            height = with(LocalDensity.current) { 24.dp.toPx() },
            frequency = 0.3f,
            offset = bottomSheetWaveOffset
        ),
        sheetBackgroundColor = DarkBlue,
        sheetContentColor = Color.White
    ) {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { padding ->
            when (state.isLoading) {
                true -> Box(modifier = Modifier.fillMaxSize())
                else -> AppNavigation(
                    modifier = modifier.padding(padding),
                    isSignedIn = state.userSession?.isSignedIn,
                    navController = navController
                )
            }
        }
    }
}

/**
 * Previews
 */

@Preview
@Composable
private fun PreviewLayout() {
    TodoAppTheme {
        MainScreen(MainState())
    }
}
