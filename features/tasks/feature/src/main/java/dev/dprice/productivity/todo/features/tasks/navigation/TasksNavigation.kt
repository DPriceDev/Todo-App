package dev.dprice.productivity.todo.features.tasks.navigation

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import dev.dprice.productivity.todo.features.groups.api.model.GroupNavLocation
import dev.dprice.productivity.todo.features.tasks.screens.add.NewContent
import dev.dprice.productivity.todo.features.tasks.screens.list.TaskListScreen
import dev.dprice.productivity.todo.features.tasks.screens.list.TaskListViewModelImpl
import dev.dprice.productivity.todo.platform.model.NavLocation
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState

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
                openGroupSelector = { navController.navigate(GroupNavLocation.GroupSelector.route) }
            )
        }



        bottomSheet(
            route = NavLocation.TasksNewContent.route,
            listOf(navArgument("groupOnly") { defaultValue = false })
        ) {
            NewContent(
                closeSheet = { navController.popBackStack() }
            )
        }
    }
}