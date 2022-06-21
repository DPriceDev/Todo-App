package dev.dprice.productivity.todo.features.tasks.navigation

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import dev.dprice.productivity.todo.features.tasks.screens.add.NewTaskViewModelImpl
import dev.dprice.productivity.todo.features.tasks.screens.list.NewContent
import dev.dprice.productivity.todo.features.tasks.screens.list.TaskListScreen
import dev.dprice.productivity.todo.features.tasks.screens.list.TaskListViewModelImpl
import dev.dprice.productivity.todo.platform.model.NavLocation

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.tasksNavigation(
    navController: NavHostController,
    modifier: Modifier,
) {
    navigation(
        route = NavLocation.Tasks.route,
        startDestination = NavLocation.TasksList.route
    ) {
        composable(NavLocation.TasksList.route) {
            val viewModel = hiltViewModel<TaskListViewModelImpl>()
            TaskListScreen(
                modifier = modifier,
                state = viewModel.state,
                onAction = viewModel::updateState,
                openAddTaskSheet = { navController.navigate(NavLocation.TasksNewContent.route) }
            )
        }

        bottomSheet(route = NavLocation.TasksNewContent.route) {
            val viewModel = hiltViewModel<NewTaskViewModelImpl>()
            NewContent(
                state = viewModel.viewState,
                onAction = { action ->
                    viewModel.updateState(action) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}