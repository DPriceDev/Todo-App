package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.dprice.productivity.todo.features.tasks.screens.add.group.NewGroupForm
import dev.dprice.productivity.todo.features.tasks.screens.add.group.NewGroupViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.habit.NewHabitForm
import dev.dprice.productivity.todo.features.tasks.screens.add.habit.NewHabitViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.model.FormType
import dev.dprice.productivity.todo.features.tasks.screens.add.task.NewTaskForm
import dev.dprice.productivity.todo.features.tasks.screens.add.task.NewTaskViewModel
import dev.dprice.productivity.todo.ui.components.SlideSelector

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NewContent() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val navController = rememberAnimatedNavController()

        val backstack by navController.currentBackStackEntryAsState()
        val currentRoute = backstack?.destination?.route

        // todo: pass in from calling location?
        val forms = listOf(
            FormType.TASK,
            FormType.HABIT,
            FormType.GROUP
        )

        val selectedIndex = forms.indexOfFirst { it.route == currentRoute }

        SlideSelector(
            forms.map { it.displayName },
            selected = if (selectedIndex == -1) 0 else selectedIndex
        ) {
            navController.navigate(forms[it].route) {
                popUpTo(forms[it].route) {
                    inclusive = true
                }
            }
        }

        NewContentNavigation(navController)

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun NewContentNavigation(
    navController: NavHostController
) {
    // todo: Build from components?
    AnimatedNavHost(
        navController = navController,
        startDestination = FormType.TASK.route
    ) {

        composable(FormType.TASK.route) {
            val viewModel: NewTaskViewModel = hiltViewModel()

            NewTaskForm(
                form = viewModel.state,
                onAction = viewModel::updateState
            )
        }

        composable(FormType.HABIT.route) {
            val viewModel: NewHabitViewModel = hiltViewModel()

            NewHabitForm(
                form = viewModel.state,
                onAction = viewModel::updateState
            )
        }

        composable(FormType.GROUP.route) {
            val viewModel: NewGroupViewModel = hiltViewModel()

            NewGroupForm(
                form = viewModel.state,
                onAction = viewModel::updateState
            )
        }
    }
}
