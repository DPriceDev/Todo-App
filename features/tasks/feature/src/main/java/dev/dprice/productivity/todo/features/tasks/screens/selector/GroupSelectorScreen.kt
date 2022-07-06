package dev.dprice.productivity.todo.features.tasks.screens.selector

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.R
import dev.dprice.productivity.todo.features.tasks.data.model.Group
import dev.dprice.productivity.todo.features.tasks.screens.list.ButtonLayout
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorState
import dev.dprice.productivity.todo.ui.components.PulsingButton
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.theme.DarkBlue
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun GroupSelectorScreen(
    state: GroupSelectorState,
    wavyState: WavyScaffoldState,
    modifier: Modifier = Modifier,
    onSelect: (Group?) -> Unit,
    onAddGroup: () -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        LaunchedEffect(key1 = Unit) {
            wavyState.animate(
                backDropHeight = maxHeight,
                waveHeight = 0.dp
            )
        }

        WavyBackdropScaffold(
            state = wavyState,
            layoutBeyondConstraints = false,
            backContent = {
                GroupSelectorContent(
                    state = state,
                    onSelect = onSelect
                )
            }
        )

        FloatingActionButton(
            onClick = onAddGroup,
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = MediumBlue
        ) {
            Icon(
                Icons.Default.Add,
                "Add Group",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun GroupSelectorContent(
    state: GroupSelectorState,
    modifier: Modifier = Modifier,
    onSelect: (Group?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .height(72.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Groups",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.h3,
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(state.groups) { index, (group, count) ->

                if (group == null) {
                    GroupButton(
                        title = "All",
                        taskCount = count,
                        onSelect = { onSelect(null) },
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    GroupButton(
                        title = group.name,
                        taskCount = count,
                        onSelect = { onSelect(group) },
                        modifier = Modifier.weight(1f),
                        colour = group.colour?.let {
                            Color(it.red, it.green, it.blue)
                        } ?: DarkBlue
                    )
                }
            }
        }
    }
}

// todo: animate height of first time
// todo: Share animation state from navigation?
@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun GroupButton(
    title: String,
    taskCount: Int,
    modifier: Modifier = Modifier,
    colour: Color = MediumBlue,
    contentColour: Color = Color.White,
    onSelect: () -> Unit
) {
    PulsingButton(
        modifier = modifier,
        backgroundColour = colour,
        onClick = onSelect
    ) {
        Column {
            ButtonLayout(
                contentColour = contentColour,
                icon = Icons.Default.DoneAll,
                text = title
            )

            Text(
                text = pluralStringResource(
                    id = R.plurals.task_count,
                    count = taskCount,
                    taskCount
                ),
                color = contentColour.copy(alpha = 0.8f),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewGroupSelectorScreen() {
    TodoAppTheme {
        BoxWithConstraints {
            GroupSelectorScreen(
                state = GroupSelectorState(),
                wavyState = WavyScaffoldState(
                    initialBackDropHeight = maxHeight
                ),
                onSelect = { },
                onAddGroup = { }
            )
        }
    }
}