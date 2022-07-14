package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.text.EntryField
import dev.dprice.productivity.todo.ui.components.text.ExpandingEntryField
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun SearchableTitleBar(
    entry: EntryField,
    isSearchShown: Boolean,
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
                isExpanded = isSearchShown,
                entry = entry,
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
        SearchableTitleBar(
            entry = EntryField(
                value = "",
                hintText = "Search Tasks",
                icon = Icons.Outlined.Search
            ),
            isSearchShown = false,
            { },
            { },
            { }
        )
    }
}