package dev.dprice.productivity.todo.features.tasks.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import dev.dprice.productivity.todo.features.tasks.screens.add.NewContent
import dev.dprice.productivity.todo.features.tasks.screens.add.NewContentViewModel
import dev.dprice.productivity.todo.features.tasks.screens.add.model.NewContentAction
import dev.dprice.productivity.todo.features.tasks.screens.list.TaskListScreen
import dev.dprice.productivity.todo.features.tasks.screens.list.TaskListViewModelImpl
import dev.dprice.productivity.todo.features.tasks.screens.selector.GroupSelectorScreen
import dev.dprice.productivity.todo.features.tasks.screens.selector.GroupSelectorViewModel
import dev.dprice.productivity.todo.platform.model.NavLocation
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.tasksNavigation(
    wavyState: WavyScaffoldState,
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
                wavyState = wavyState,
                onAction = viewModel::updateState,
                openAddTaskSheet = { navController.navigate(NavLocation.TasksNewContent.route) },
                openGroupSelector = { navController.navigate(NavLocation.TasksGroup.route) }
            )
        }

        composable(NavLocation.TasksGroup.route) {
            val viewModel = hiltViewModel<GroupSelectorViewModel>()

            LaunchedEffect(viewModel.state.isDismissed) {
                if (viewModel.state.isDismissed) navController.popBackStack()
            }

            GroupSelectorScreen(
                state = viewModel.state,
                wavyState = wavyState,
                modifier = modifier,
                onSelect = viewModel::selectGroup,
                onAddGroup = {
                    navController.navigate(NavLocation.TasksNewContent.withArguments(true))
                }
            )
        }

        bottomSheet(
            route = NavLocation.TasksNewContent.route,
            listOf(navArgument("groupOnly") { defaultValue = false })
        ) { backstackEntry ->
            val viewModel = hiltViewModel<NewContentViewModel>()

            val isGroupOnly = backstackEntry.arguments?.getBoolean("groupOnly")
            LaunchedEffect(key1 = isGroupOnly) {
                isGroupOnly?.let {
                    if (isGroupOnly) {
                        viewModel.updateState(NewContentAction.ShowGroupOnly)
                    }
                }
            }

            LaunchedEffect(viewModel.state.isDismissed) {
                if (viewModel.state.isDismissed) navController.popBackStack()
            }

            NewContent(
                state = viewModel.state,
                onAction = viewModel::updateState
            )
        }
    }
}