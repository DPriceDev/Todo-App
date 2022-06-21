package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

data class TitleBarState(
    val searchEntry: EntryField = EntryField(
        value = "",
        hintText = "Search Tasks",
        icon = Icons.Outlined.Search
    ),
    val isSearchShown: Boolean = false
)

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