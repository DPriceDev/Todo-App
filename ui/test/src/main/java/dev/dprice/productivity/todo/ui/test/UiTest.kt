package dev.dprice.productivity.todo.ui.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule

abstract class UiActivityTest : UiTest<UiTestActivity>(UiTestActivity::class.java)

abstract class UiTest<A : ComponentActivity>(activity: Class<A>) {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule(activity)

    @Before
    fun beforeTest() {
        hiltRule.inject()
    }
}
