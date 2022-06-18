package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TitleBarState
import dev.dprice.productivity.todo.ui.components.ExpandingEntryField
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme


@Composable
fun SearchableTitleBar(
    state: TitleBarState = TitleBarState(),
    onTextChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onSearchClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        Text(
            text = "Tasks",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.h3
        )

        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            ExpandingEntryField(
                isExpanded = state.isSearchShown,
                entry = state.searchEntry,
                onFocusChange = onFocusChange,
                onTextChange = onTextChange,
                onExpandClicked = onSearchClick
            )
        }
    }
}


@Preview
@Composable
fun PreviewTitleBar() {
    TodoAppTheme {
        SearchableTitleBar(TitleBarState(), { }, { }) { }
    }
}