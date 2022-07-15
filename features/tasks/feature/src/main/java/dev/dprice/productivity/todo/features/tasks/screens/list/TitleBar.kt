package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.group.asColour
import dev.dprice.productivity.todo.features.tasks.screens.add.group.asImageVector
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskFilter
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TitleBarState
import dev.dprice.productivity.todo.ui.components.PulsingButton
import dev.dprice.productivity.todo.ui.components.SearchableTitleBar
import dev.dprice.productivity.todo.ui.components.Shimmer
import dev.dprice.productivity.todo.ui.components.SlideSelector
import dev.dprice.productivity.todo.ui.theme.DarkBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun TitleBar(
    state: TitleBarState,
    openGroupSelector: () -> Unit,
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

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            PulsingButton(
                modifier = Modifier.weight(1f),
                backgroundColour = state.currentGroup?.colour?.asColour() ?: DarkBlue,
                onClick = openGroupSelector
            ) {
                Shimmer {
                    ButtonLayout(
                        contentColour = Color.White,
                        icon = state.currentGroup?.icon?.asImageVector() ?: Icons.Default.DoneAll,
                        text = state.currentGroup?.name ?: "All"
                    )
                }
            }

            PulsingButton(
                modifier = Modifier.weight(1f),
                backgroundColour = DarkBlue,
                onClick = { /* todo: Open Date Range selector */ }
            ) {
                ButtonLayout(
                    icon = Icons.Default.DateRange,
                    contentColour = Color.White,
                    text = "All"
                )
            }
        }

        Spacer(modifier = Modifier.heightIn(8.dp))

        // Task filters
        SlideSelector(
            TaskFilter.values().map { stringResource(id = it.displayNameId) },
            selected = state.filter.ordinal,
            onSelected = { onAction(TaskListAction.UpdateFilter(TaskFilter.values()[it])) },
            selectedColor = DarkBlue,
            selectedContentColor = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        //DateSelector()
    }
}

@Composable
private fun DateSelector() {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(imageVector = Icons.Default.ArrowLeft, contentDescription = null)
        Text(
            text = "Today",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )
        Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
    }
}

@Composable
fun ButtonLayout(
    icon: ImageVector,
    contentColour: Color,
    text: String
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColour,
        )
        Text(
            text = text,
            color = contentColour,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Preview
@Composable
private fun PreviewTitleBar() {
    TodoAppTheme {
        TitleBar(
            state = TitleBarState(),
            openGroupSelector = { /*TODO*/ },
            onAction = { }
        )
    }
}