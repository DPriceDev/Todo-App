package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.components.RoundedEntryCard
import dev.dprice.productivity.todo.ui.theme.DarkBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

data class SearchableTitleBarState(
    val entry: EntryField = EntryField(
        value = "",
        hintText = "Search Tasks",
        icon = Icons.Outlined.Search
    ),
    val isSearchShown: Boolean = false
)

enum class SearchBarState {
    ICON_BUTTON,
    EXPANDING_TO_CIRCLE,
    EXPANDING_TO_TEXT,
    SHRINK_TO_CIRCLE,
    SHRINK_TO_ICON_BUTTON
}

@Composable
fun SearchableTitleBar(
    state: SearchableTitleBarState = SearchableTitleBarState(),
    onTextChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onSearchClick: () -> Unit
) {
    var animationState: SearchBarState by remember {
        mutableStateOf(SearchBarState.ICON_BUTTON)
    }

    LaunchedEffect(key1 = state.isSearchShown) {
        animationState = if (state.isSearchShown) {
            when (animationState) {
                SearchBarState.ICON_BUTTON -> SearchBarState.EXPANDING_TO_CIRCLE
                else -> SearchBarState.EXPANDING_TO_TEXT
            }
        } else {
            when (animationState) {
                SearchBarState.EXPANDING_TO_TEXT -> SearchBarState.SHRINK_TO_CIRCLE
                SearchBarState.SHRINK_TO_CIRCLE -> SearchBarState.SHRINK_TO_ICON_BUTTON
                else -> SearchBarState.ICON_BUTTON
            }
        }
    }

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
            ExpandingSearchBar(
                entry = state.entry,
                state = animationState,
                onFocusChange = onFocusChange,
                onTextChange = onTextChange,
                onSearchClick = onSearchClick,
                onAnimationUpdate = {
                    animationState = when (state.isSearchShown) {
                        true -> SearchBarState.EXPANDING_TO_TEXT
                        false -> when(animationState) {
                            SearchBarState.EXPANDING_TO_TEXT -> SearchBarState.SHRINK_TO_CIRCLE
                            SearchBarState.SHRINK_TO_CIRCLE -> SearchBarState.SHRINK_TO_ICON_BUTTON
                            else -> SearchBarState.ICON_BUTTON
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ExpandingSearchBar(
    state: SearchBarState,
    entry: EntryField,
    modifier: Modifier = Modifier,
    iconSize: Dp = 72.dp,
    onTextChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onSearchClick: () -> Unit,
    onAnimationUpdate: () -> Unit
) {
    when (state) {
        SearchBarState.SHRINK_TO_CIRCLE,
        SearchBarState.EXPANDING_TO_TEXT -> SearchBarTextField(
            state,
            entry,
            modifier,
            iconSize,
            onAnimationUpdate,
            onTextChange,
            onFocusChange

        )
        else -> SearchBarIcon(
            state,
            modifier,
            iconSize,
            onSearchClick,
            onAnimationUpdate
        )
    }
}

@Composable
private fun SearchBarTextField(
    state: SearchBarState,
    entry: EntryField,
    modifier: Modifier = Modifier,
    iconSize: Dp = 72.dp,
    onAnimationFinished: () -> Unit = { },
    onTextChange: (String) -> Unit = { },
    onFocusChange: (Boolean) -> Unit = { }
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        var targetWidth: Dp by remember { mutableStateOf(iconSize) }
        val width: Dp by animateDpAsState(
            targetValue = targetWidth,
            animationSpec = tween(
                durationMillis = 150,
                easing = FastOutSlowInEasing
            ),
            finishedListener = { onAnimationFinished() }
        )

        val focusRequester: FocusRequester = remember { FocusRequester() }
        LaunchedEffect(key1 = state) {
            targetWidth = when (state) {
                SearchBarState.EXPANDING_TO_TEXT -> {
                    focusRequester.requestFocus()
                    maxWidth
                }
                else -> {
                    focusRequester.freeFocus()
                    iconSize
                }
            }
        }

        val focusManager = LocalFocusManager.current
        RoundedEntryCard(
            modifier = Modifier
                .width(width)
                .focusRequester(focusRequester)
                .onFocusChanged { onFocusChange(it.hasFocus) }
                .padding(8.dp),
            entry = entry,
            borderColour = DarkBlue,
            leadingIconPadding = PaddingValues(start = 8.dp),
            onTextChanged = onTextChange,
            onImeAction = { focusManager.clearFocus() }
        )
    }
}

@Composable
private fun SearchBarIcon(
    animationState: SearchBarState,
    modifier: Modifier = Modifier,
    iconSize: Dp = 72.dp,
    onSearchClick: () -> Unit = { },
    onAnimationFinished: () -> Unit = { },
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(iconSize)
            .padding(8.dp)
            .then(modifier)
    ) {
        LaunchedEffect(key1 = animationState) {
            if (animationState == SearchBarState.SHRINK_TO_ICON_BUTTON) {
                onAnimationFinished()
            }
        }

        val size: Dp by animateDpAsState(
            finishedListener = { onAnimationFinished() },
            targetValue = when (animationState) {
                SearchBarState.ICON_BUTTON -> 0.dp
                else -> iconSize - 16.dp
            },
            animationSpec = tween(
                durationMillis = 80,
                easing = LinearEasing
            )
        )

        Box(
            modifier = Modifier
                .size(size)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(percent = 50)
                )
        )

        when (animationState) {
            SearchBarState.ICON_BUTTON -> IconButton(
                onClick = onSearchClick,
                modifier = modifier
            ) {
                Icon(
                    Icons.Outlined.Search,
                    null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp)
                )
            }
            else -> Icon(
                Icons.Outlined.Search,
                null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
            )
        }
    }

    Spacer(modifier = Modifier.width(8.dp))
}

@Preview
@Composable
fun PreviewTitleBar() {
    TodoAppTheme {
        SearchableTitleBar(SearchableTitleBarState(), { }, { }) { }
    }
}

@Preview
@Composable
fun PreviewTitleBarWithSearchBox() {
    TodoAppTheme {
        SearchableTitleBar(SearchableTitleBarState(isSearchShown = true), { }, { }) { }
    }
}