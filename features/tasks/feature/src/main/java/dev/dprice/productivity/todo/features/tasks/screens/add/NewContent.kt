package dev.dprice.productivity.todo.features.tasks.screens.add

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.dprice.productivity.todo.features.tasks.screens.add.group.NewGroupForm
import dev.dprice.productivity.todo.features.tasks.screens.add.group.NewGroupViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.habit.NewHabitForm
import dev.dprice.productivity.todo.features.tasks.screens.add.habit.NewHabitViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentState
import dev.dprice.productivity.todo.features.tasks.screens.add.task.NewTaskForm
import dev.dprice.productivity.todo.features.tasks.screens.add.task.NewTaskViewModel
import dev.dprice.productivity.todo.ui.components.SlideSelector

@Composable
fun NewContent(
    state: NewContentState,
    onAction: (NewContentAction) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // todo: Pick up from args
        // navigate on click
        // todo: get current form destination by state?
        if (state.forms.size > 1) {
            SlideSelector(
                state.forms.map { it.displayName },
                selected = state.forms.indexOf(state.selectedForm)
            ) {
                onAction(NewContentAction.SelectContentType(it))
            }
        } else {
            Spacer(modifier = Modifier.height(8.dp))
        }

        NewContentNavigation()

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun NewContentNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "task"
    ) {

        composable("task") {
            val viewModel: NewTaskViewModel = hiltViewModel()

            NewTaskForm(
                form = viewModel.state,
                onAction = viewModel::updateState
            )
        }

        composable("habit") {
            val viewModel: NewHabitViewModel = hiltViewModel()

            NewHabitForm(
                form = viewModel.state,
                onAction = viewModel::updateState
            )
        }

        composable("group") {
            val viewModel: NewGroupViewModel = hiltViewModel()

            NewGroupForm(
                form = viewModel.state,
                onAction = viewModel::updateState
            )
        }
    }
}
