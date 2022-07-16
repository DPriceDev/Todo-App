package dev.dprice.productivity.todo.features.tasks.screens.selector

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.R
import dev.dprice.productivity.todo.features.tasks.data.model.Group
import dev.dprice.productivity.todo.features.tasks.screens.add.group.asColour
import dev.dprice.productivity.todo.features.tasks.screens.add.group.asImageVector
import dev.dprice.productivity.todo.features.tasks.screens.list.ButtonLayout
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorAction
import dev.dprice.productivity.todo.features.tasks.screens.selector.model.GroupSelectorState
import dev.dprice.productivity.todo.ui.components.animated.PulsingLayout
import dev.dprice.productivity.todo.ui.components.scaffold.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun GroupSelectorScreen(
    state: GroupSelectorState,
    wavyState: WavyScaffoldState,
    modifier: Modifier = Modifier,
    onAddGroup: () -> Unit,
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
                    state = state,
                    onSelect = { onAction(GroupSelectorAction.SelectGroup(it?.id)) },
                    onLongPress = { onAction(GroupSelectorAction.LongPressGroup(it?.id)) },
                    onDelete = { onAction(GroupSelectorAction.DeleteGroups) }
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
    onSelect: (Group?) -> Unit,
    onDelete: () -> Unit,
    onLongPress: (Group?) -> Unit
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
            IconButton(
                onClick = onDelete,
                enabled = state.groups.any { it.isSelected },
                modifier = Modifier.padding(8.dp).align(Alignment.CenterEnd)
            ) {
                this@Column.AnimatedVisibility(
                    visible = state.isEditMode,
                    enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                    exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete ${state.groups.count { it.isSelected }} Groups", // todo: Plural string
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.groups) { (group, count, isSelected) ->
                GroupButton(
                    title = group?.name ?: "All",
                    isSelected = isSelected,
                    taskCount = count,
                    onSelect = { onSelect(group) },
                    colour = group?.colour?.asColour() ?: MediumBlue,
                    icon = group?.icon?.asImageVector() ?: Icons.Default.DoneAll,
                    onLongClick = { group?.let { onLongPress(it) } },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// todo: animate height of first time
// todo: Share animation state from navigation?
@Composable
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
private fun GroupButton(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    taskCount: Int,
    modifier: Modifier = Modifier,
    colour: Color = MediumBlue,
    contentColour: Color = Color.White,
    onSelect: () -> Unit,
    onLongClick: () -> Unit
) {
    PulsingLayout {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 32.dp))
                .combinedClickable(
                    onClick = onSelect,
                    onLongClick = onLongClick
                )
                .then(modifier),
            border = BorderStroke(
                color = if (isSelected) Color.Black else Color.Unspecified,
                width = 3.dp
            ),
            color = colour,
            shape = RoundedCornerShape(size = 32.dp)
        ) {
            Column {
                ButtonLayout(
                    contentColour = contentColour,
                    icon = icon,
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
}

@Preview
@Composable
private fun PreviewGroupSelectorScreen() {
    TodoAppTheme {
        BoxWithConstraints {
            GroupSelectorScreen(
                state = GroupSelectorState(
                    messageFlow = MutableSharedFlow()
                ),
                wavyState = WavyScaffoldState(
                    initialBackDropHeight = maxHeight
                ),
                onAction = { },
                onAddGroup = { }
            )
        }
    }
}