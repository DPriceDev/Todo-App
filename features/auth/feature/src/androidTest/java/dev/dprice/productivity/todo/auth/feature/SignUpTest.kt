package dev.dprice.productivity.todo.auth.feature

import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import dev.dprice.productivity.todo.auth.feature.ui.AuthScreen
import dev.dprice.productivity.todo.ui.test.UiActivityTest
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignUpTest : UiActivityTest() {

    @Test
    fun testThing() {
        composeRule.setContent {
            TodoAppTheme {
                val navController = rememberNavController()
                AuthScreen(navController = navController)
            }
        }
    }


}