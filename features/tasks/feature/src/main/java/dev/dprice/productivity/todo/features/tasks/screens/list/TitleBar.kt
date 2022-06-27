package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskFilter
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskListAction
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TitleBarState
import dev.dprice.productivity.todo.ui.components.SearchableTitleBar
import dev.dprice.productivity.todo.ui.components.SlideSelector
import dev.dprice.productivity.todo.ui.theme.DarkBlue

@Composable
fun TitleBar(
    state: TitleBarState,
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

        BoxWithConstraints {
            // todo: could I extract the shimmer to a seperate function or modifier?
            val shimmerAnimation by rememberInfiniteTransition().animateFloat(
                initialValue = 0.0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    tween(durationMillis = 4000, easing = FastOutSlowInEasing),
                    RepeatMode.Restart
                )
            )

            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PulsingButton(
                    modifier = Modifier.weight(1f),
                    backgroundColour = Color.Green,
                    contentColour = Color.Black,
                    icon = Icons.Default.DoneAll,
                    text = "All",
                    shimmerProgress = shimmerAnimation
                )
                PulsingButton(
                    modifier = Modifier.weight(1f),
                    backgroundColour = DarkBlue,
                    contentColour = Color.White,
                    icon = Icons.Default.DateRange,
                    text = "All"
                )
            }
        }

        Spacer(modifier = Modifier.heightIn(16.dp))

        // Task filters
        SlideSelector(
            *TaskFilter.values()
                .map { stringResource(id = it.displayNameId) }
                .toTypedArray(),
            selected = state.filter.ordinal,
            onSelected = { onAction(TaskListAction.UpdateFilter(TaskFilter.values()[it])) },
            selectedColor = DarkBlue,
            selectedContentColor = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Date Selector
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
}

@Composable
private fun PulsingButton(
    text: String,
    icon: ImageVector,
    backgroundColour: Color,
    contentColour: Color,
    modifier: Modifier = Modifier,
    shimmerProgress: Float = 0.0f
) {
        val infiniteTransition = rememberInfiniteTransition()
        val pulseAnimation by infiniteTransition.animateFloat(
            initialValue = 0.98f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 3000, easing = FastOutSlowInEasing),
                RepeatMode.Reverse
            )
        )

        Card(
            shape = RoundedCornerShape(percent = 50),
            backgroundColor = backgroundColour,
            elevation = 8.dp,
            modifier = Modifier
                .scale(pulseAnimation)
                .then(modifier)
        ) {
            BoxWithConstraints {
                val shimmerWidth = constraints.maxWidth * 0.20f
                val shimmerAnimation = -shimmerWidth + (constraints.maxWidth + (2 * shimmerWidth)) * shimmerProgress

                val shimmerColorShades = listOf(
                    Color.Unspecified,
                    Color.White.copy(0.5f),
                    Color.Unspecified,
                )

                val brush = Brush.linearGradient(
                    colors = shimmerColorShades,
                    start = Offset(shimmerAnimation, shimmerAnimation),
                    end = Offset(shimmerAnimation + shimmerWidth, shimmerAnimation + shimmerWidth),
                    tileMode = TileMode.Decal
                )
                Box(
                    modifier = Modifier.background(brush = brush)
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
            }
        }
}