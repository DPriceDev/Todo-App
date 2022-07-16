package dev.dprice.productivity.todo.features.groups.feature.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.GroupSelectorScreen
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.GroupSelectorViewModel
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.model.GroupSelectorAction
import dev.dprice.productivity.todo.platform.model.NavLocation
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState
import dev.dprice.productivity.todo.ui.compositions.LocalSnackBarHostState

fun NavGraphBuilder.groupsNavigation(
    wavyState: WavyScaffoldState,
    navController: NavHostController,
    modifier: Modifier,
) {
    navigation(
        route = NavLocation.Groups.route,
        startDestination = NavLocation.TasksGroup.route
    ) {

        composable(NavLocation.TasksGroup.route) {
            val viewModel = hiltViewModel<GroupSelectorViewModel>()

            LaunchedEffect(viewModel.state.isDismissed) {
                if (viewModel.state.isDismissed) navController.popBackStack()
            }

            BackHandler(
                enabled = viewModel.state.isEditMode
            ) {
                viewModel.onAction(GroupSelectorAction.ExitEditMode)
            }

            val localSnackBar = LocalSnackBarHostState.current
            LaunchedEffect(key1 = viewModel.state.messageFlow) {

                viewModel.state.messageFlow.collect { message ->
                    val result = localSnackBar.showSnackbar(
                        message,
                        actionLabel = "Undo"
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onAction(GroupSelectorAction.UndoDelete)
                    }
                }
            }

            GroupSelectorScreen(
                state = viewModel.state,
                wavyState = wavyState,
                modifier = modifier,
                onAction = viewModel::onAction,
                onAddGroup = {
                    navController.navigate(NavLocation.TasksNewContent.withArguments(true))
                }
            )
        }
    }
}