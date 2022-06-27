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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.list.ButtonLayout
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorAction
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorState
import dev.dprice.productivity.todo.ui.components.PulsingButton
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun GroupSelectorScreen(
    state: GroupSelectorState,
    wavyState: WavyScaffoldState,
    modifier: Modifier = Modifier,
    onAction: (GroupSelectorAction) -> Unit
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
                    state = state
                )
            },
            frontContent = { }
        )

        FloatingActionButton(
            onClick = { /* onAction() */ },
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
    modifier: Modifier = Modifier
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
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            itemsIndexed(state.groups) { index, group ->

                // todo: animate height of first time
                // todo: Share animation state from navigation?
                PulsingButton(
                    modifier = Modifier.weight(1f),
                    backgroundColour = group.colour?.let {
                        Color(it.red, it.green, it.blue)
                    } ?: MediumBlue,
                    onClick = { /* todo: select group */ }
                ) {
                    Column {
                        ButtonLayout(
                            contentColour = Color.Black,
                            icon = Icons.Default.DoneAll,
                            text = group.name
                        )
                        Text(
                            text = "4 tasks",
                            color = Color.Black.copy(alpha = 0.5f),
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .padding(bottom = 24.dp)
                        )
                    }
                }
            }
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
                onAction = { }
            )
        }
    }
}