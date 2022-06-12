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
import androidx.compose.ui.text.style.TextAlign
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

enum class TitleBarAnimationState {
    TITLE,
    SEARCH_EXPAND_CIRCLE,
    SEARCH
}

@Composable
fun SearchableTitleBar(
    state: SearchableTitleBarState = SearchableTitleBarState(),
    onTextChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onSearchClick: () -> Unit
) {
    var animationState: TitleBarAnimationState by remember {
        mutableStateOf(TitleBarAnimationState.TITLE)
    }

    LaunchedEffect(key1 = state.isSearchShown) {
        animationState = if(state.isSearchShown) {
            when(animationState) {
                TitleBarAnimationState.TITLE -> TitleBarAnimationState.SEARCH_EXPAND_CIRCLE
                else -> TitleBarAnimationState.SEARCH
            }
        } else {
            when(animationState) {
                TitleBarAnimationState.SEARCH -> TitleBarAnimationState.SEARCH_EXPAND_CIRCLE
                else -> TitleBarAnimationState.TITLE
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        if (animationState == TitleBarAnimationState.SEARCH) {
            SearchBar(
                state.entry,
                modifier = Modifier.align(Alignment.CenterEnd),
                animationState = animationState,
                onFocusChange = onFocusChange,
                onTextChange = onTextChange,
                onAnimationUpdate = {
                    animationState = when(state.isSearchShown) {
                        true -> TitleBarAnimationState.SEARCH
                        false -> TitleBarAnimationState.TITLE
                    }
                }
            )
        } else {
            TitleBar(
                animationState = animationState,
                onAnimationUpdate = {
                    animationState = when(state.isSearchShown) {
                        true -> TitleBarAnimationState.SEARCH
                        false -> TitleBarAnimationState.TITLE
                    }
                },
                onSearchClick = onSearchClick
            )
        }
    }
}

// todo: Better to use slots for left and right icons and then move the search icon animation
// to the search bar

@Composable
private fun TitleBar(
    animationState: TitleBarAnimationState,
    modifier: Modifier = Modifier,
    onAnimationUpdate: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Text(
            text = "Tasks",
            modifier = Modifier
                .padding(start = 64.dp)
                .weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(56.dp)
        ) {
            val size: Dp by animateDpAsState(
                targetValue = when(animationState) {
                    TitleBarAnimationState.TITLE -> 0.dp
                    else -> 56.dp
                },
                animationSpec = tween(
                    durationMillis = 80,
                    easing = LinearEasing
                ),
                finishedListener = { onAnimationUpdate() }
            )

            Box(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .size(size)
            )

            IconButton(
                onClick = onSearchClick
            ) {
                Icon(
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
}

@Composable
private fun SearchBar(
    entry: EntryField,
    animationState: TitleBarAnimationState,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onAnimationUpdate: () -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val fullSize = 72.dp
        var targetWidth: Dp by remember { mutableStateOf(fullSize) }
        val width: Dp by animateDpAsState(
            targetValue = targetWidth,
            animationSpec = tween(
                durationMillis = 150,
                easing = FastOutSlowInEasing
            ),
            finishedListener = { onAnimationUpdate() }
        )

        val focusRequester: FocusRequester = remember { FocusRequester() }
        LaunchedEffect(key1 = animationState) {
            targetWidth = when (animationState) {
                TitleBarAnimationState.SEARCH -> {
                    focusRequester.requestFocus()
                    maxWidth
                }
                else -> {
                    focusRequester.freeFocus()
                    fullSize
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