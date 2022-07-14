package dev.dprice.productivity.todo.ui.components.text

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.DarkBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

enum class ExpandingEntryState {
    ICON_BUTTON,
    EXPANDING_TO_CIRCLE,
    EXPANDING_TO_TEXT,
    SHRINK_TO_CIRCLE,
    SHRINK_TO_ICON_BUTTON;

    fun nextState(isExpanded: Boolean) : ExpandingEntryState {
        return when (isExpanded) {
            true -> when (this) {
                ICON_BUTTON -> EXPANDING_TO_CIRCLE
                else -> EXPANDING_TO_TEXT
            }
            false -> when (this) {
                EXPANDING_TO_TEXT -> SHRINK_TO_CIRCLE
                SHRINK_TO_CIRCLE -> SHRINK_TO_ICON_BUTTON
                else -> ICON_BUTTON
            }
        }
    }
}

@Composable
fun ExpandingEntryField(
    isExpanded: Boolean,
    entry: EntryField,
    modifier: Modifier = Modifier,
    expandingEntryState: MutableState<ExpandingEntryState> = remember {
        mutableStateOf(ExpandingEntryState.ICON_BUTTON)
    },
    iconSize: Dp = 72.dp,
    onTextChange: (String) -> Unit = { },
    onFocusChange: (Boolean) -> Unit = { },
    onExpandClicked: () -> Unit = { }
) {
    var state: ExpandingEntryState by expandingEntryState

    LaunchedEffect(key1 = isExpanded) {
        state = state.nextState(isExpanded)
    }

    when (state) {
        ExpandingEntryState.SHRINK_TO_CIRCLE,
        ExpandingEntryState.EXPANDING_TO_TEXT -> ExpandingEntryTextField(
            state = state,
            entry = entry,
            modifier = modifier,
            iconSize = iconSize,
            onTextChange = onTextChange,
            onFocusChange = onFocusChange,
            onAnimationFinished = { state = state.nextState(isExpanded) }

        )
        else -> ExpandingFieldIcon(
            state = state,
            modifier = modifier,
            iconSize = iconSize,
            icon = entry.icon,
            onSearchClick = onExpandClicked,
            onAnimationFinished = { state = state.nextState(isExpanded) }
        )
    }
}

@Composable
private fun ExpandingEntryTextField(
    state: ExpandingEntryState,
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
                ExpandingEntryState.EXPANDING_TO_TEXT -> {
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
private fun ExpandingFieldIcon(
    state: ExpandingEntryState,
    modifier: Modifier = Modifier,
    iconSize: Dp = 72.dp,
    icon: ImageVector? = null,
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
        // There is not an animation between shrink to icon and icon button, state exists to set
        // up the expanded state to shrink from.
        LaunchedEffect(key1 = state) {
            if (state == ExpandingEntryState.SHRINK_TO_ICON_BUTTON) {
                onAnimationFinished()
            }
        }

        val size: Dp by animateDpAsState(
            finishedListener = { onAnimationFinished() },
            targetValue = when (state) {
                ExpandingEntryState.ICON_BUTTON -> 0.dp
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

        icon?.let {
            when (state) {
                ExpandingEntryState.ICON_BUTTON -> IconButton(
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
    }

    Spacer(modifier = Modifier.width(8.dp))
}

/* Previews */

private class ExpandingEntryFieldProvider: PreviewParameterProvider<ExpandingEntryState> {
    override val values: Sequence<ExpandingEntryState> = ExpandingEntryState.values().asSequence()
}

@Preview
@Composable
private fun PreviewExpandingEntryCard(
    @PreviewParameter(ExpandingEntryFieldProvider::class) state: ExpandingEntryState
) {
    val entry = EntryField(
        value = "",
        hintText = "Search Tasks",
        icon = Icons.Outlined.Search
    )

    TodoAppTheme {
        Box(
            modifier = Modifier.fillMaxWidth().height(72.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            ExpandingEntryField(
                isExpanded = true,
                entry = entry,
                expandingEntryState = mutableStateOf(state)
            )
        }
    }
}