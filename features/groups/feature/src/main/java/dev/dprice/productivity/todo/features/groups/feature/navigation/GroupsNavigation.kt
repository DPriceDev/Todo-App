package dev.dprice.productivity.todo.features.groups.feature.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import dev.dprice.productivity.todo.features.groups.api.model.GroupNavLocation
import dev.dprice.productivity.todo.features.groups.feature.screens.form.NewGroupForm
import dev.dprice.productivity.todo.features.groups.feature.screens.form.NewGroupViewModel
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.GroupSelectorScreen
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.GroupSelectorViewModel
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.model.GroupSelectorAction
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState
import dev.dprice.productivity.todo.ui.compositions.LocalSnackBarHostState

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.groupsNavigation(
    wavyState: WavyScaffoldState,
    navController: NavHostController,
    modifier: Modifier,
) {
    navigation(
        route = GroupNavLocation.Groups.route,
        startDestination = GroupNavLocation.GroupSelector.route
    ) {

        composable(GroupNavLocation.GroupSelector.route) {
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
                    navController.navigate(GroupNavLocation.NewGroup.route)
                }
            )
        }

        bottomSheet(
            route = GroupNavLocation.NewGroup.route
        ) {
            val viewModel: NewGroupViewModel = hiltViewModel()

            LaunchedEffect(key1 = viewModel.state.isDismissed) {
                if (viewModel.state.isDismissed) navController.popBackStack()
            }

            NewGroupForm(
                state = viewModel.state,
                modifier = Modifier.padding(8.dp),
                onAction = viewModel::updateState
            )
        }
    }
}