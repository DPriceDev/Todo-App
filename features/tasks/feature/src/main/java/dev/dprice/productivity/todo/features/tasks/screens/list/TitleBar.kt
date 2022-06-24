package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskFilter
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TitleBarState
import dev.dprice.productivity.todo.ui.components.SearchableTitleBar
import dev.dprice.productivity.todo.ui.components.SlideSelector
import dev.dprice.productivity.todo.ui.theme.DarkBlue

@Composable
fun TitleBar(
    state: TitleBarState,
    onAction: (TaskListAction) -> Unit
) {
    Column {
        SearchableTitleBar(
            entry = state.searchEntry,
            isSearchShown = state.isSearchShown,
            onTextChange = { onAction(TaskListAction.UpdateSearchText(it)) },
            onFocusChange = { onAction(TaskListAction.UpdateSearchFocus(it)) },
            onSearchClick = { onAction(TaskListAction.SearchButtonClicked) }
        )

        SlideSelector(
            *TaskFilter.values()
                .map { stringResource(id = it.displayNameId) }
                .toTypedArray(),
            selected = state.filter.ordinal,
            onSelected = { onAction(TaskListAction.UpdateFilter(TaskFilter.values()[it])) },
            selectedColor = DarkBlue,
            selectedContentColor = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}