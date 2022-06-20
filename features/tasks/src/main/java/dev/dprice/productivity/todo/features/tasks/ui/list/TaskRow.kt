package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskState
import dev.dprice.productivity.todo.features.tasks.ui.list.preview.TaskStatePreviewProvider
import dev.dprice.productivity.todo.platform.util.asTaskDateString
import dev.dprice.productivity.todo.ui.components.RoundedButton
import dev.dprice.productivity.todo.ui.components.VerticalDivider
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.completeColour
import kotlin.math.roundToInt

enum class SwipeState {
    CLOSED,
    SWIPED,
    GONE
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskRow(
    task: TaskState,
    index: Int,
    modifier: Modifier = Modifier,
    swipeableState: SwipeableState<Boolean> = rememberSwipeableState(initialValue = task.isSwiped),
    onCompleteTaskClick: () -> Unit = { },
    onSwipeTask: () -> Unit = { },
    onClicked: () -> Unit
) {
    val visibility: Float by animateFloatAsState(
        targetValue = if (task.isSwiped) 0f else 1f,
        animationSpec = tween(200),
        finishedListener = { if (it == 0f) onCompleteTaskClick() }
    )

    LaunchedEffect(key1 = swipeableState.currentValue) {
        if (swipeableState.currentValue) onSwipeTask()
    }

    LaunchedEffect(key1 = task.isSwiped) {
        if (!task.isSwiped) swipeableState.snapTo(false)
    }

    val focusRequester = remember { FocusRequester() }
    Box(
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusable()
            .height(IntrinsicSize.Min)
            .alpha(visibility)
            .then(modifier)
    ) {
        if (!task.isSelected) {
            Card(
                elevation = 4.dp,
                contentColor = Color.White,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                backgroundColor = completeColour
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Task Complete!",
                        style = MaterialTheme.typography.h3
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        Icons.Default.Check,
                        null,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxHeight()
                            .padding(4.dp)
                    )
                }
            }
        }

        Box {
            val maxSwipe = with(LocalDensity.current) { 256.dp.toPx() }
            Card(
                elevation = 8.dp,
                onClick = {
                    if (!task.isSelected) focusRequester.requestFocus()
                    onClicked()
                },
                backgroundColor = MediumBlue,
                contentColor = Color.White,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .animateContentSize()
                    .padding(4.dp)
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .swipeable(
                        state = swipeableState,
                        thresholds = { _, _ -> FractionalThreshold(0.9f) },
                        enabled = !swipeableState.currentValue && !task.task.isComplete && !task.isSelected,
                        anchors = mapOf(
                            -maxSwipe to true,
                            0f to false
                        ),
                        orientation = Orientation.Horizontal,
                    )
            ) {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VerticalDivider()

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = task.task.title)
                                Text(text = task.task.dateTime.asTaskDateString())
                            }

                            TaskStatusIcon(
                                task.task.isComplete,
                                index = index,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                        if (task.isSelected) {
                            ExpandedTaskContent(
                                task,
                                onCompleteTaskClick
                            )
                        }

                        ExpandedArrowIcon(
                            isSelected = task.isSelected,
                            modifier = modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

// todo: separate out icon animation
@Composable
private fun TaskStatusIcon(
    isComplete: Boolean,
    modifier: Modifier = Modifier,
    index: Int = 0,
    pulseDuration: Int = 2000
) {
    Box(
        modifier = Modifier
            .padding(end = 12.dp)
            .then(modifier)
    ) {
        val scale: Float by animateFloatAsState(
            targetValue = if (isComplete) 1.0f else 0.0f,
            animationSpec = tween(
                durationMillis = 300
            )
        )

        val rotation: Float by animateFloatAsState(
            targetValue = if (isComplete) 360f else 0.0f,
            animationSpec = tween(
                durationMillis = 300
            )
        )

        val scaleWave: Float by rememberInfiniteTransition().animateFloat(
            initialValue = 1.0f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                repeatMode = RepeatMode.Reverse,
                animation = tween(
                    durationMillis = pulseDuration,
                    easing = LinearEasing
                ),
                initialStartOffset = StartOffset(
                    (pulseDuration * (index / 4f)).toInt(),
                    StartOffsetType.FastForward
                )
            )
        )

        if (scale != 1f) {
            Icon(
                Icons.Outlined.Circle,
                null,
                Modifier.size(24.dp)
            )
        }

        if (scale != 0f) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .scale(scale * scaleWave)
                    .rotate(rotation),
            ) {
                Icon(
                    Icons.Filled.Circle,
                    null,
                    Modifier.size(24.dp),
                    tint = completeColour
                )
                Icon(
                    Icons.Default.Check,
                    null,
                    Modifier
                        .size(24.dp)
                        .padding(4.dp),
                )
            }
        }
    }
}

@Composable
private fun ExpandedTaskContent(
    task: TaskState,
    onCompleteTaskClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (task.task.description.isNotEmpty()) {
            Text(text = "Description")
            Text(text = task.task.description)
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoundedButton(
                onClick = onCompleteTaskClick,
                modifier = Modifier.padding(horizontal = 12.dp),
                contentPadding = PaddingValues(
                    vertical = 12.dp
                )
            ) {
                Icon(
                    Icons.Default.Done,
                    null
                )
            }

            RoundedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(horizontal = 12.dp),
                contentPadding = PaddingValues(
                    vertical = 12.dp
                )
            ) {
                Icon(
                    Icons.Default.Edit,
                    null
                )
            }

            RoundedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(horizontal = 12.dp),
                contentPadding = PaddingValues(
                    vertical = 12.dp
                )
            ) {
                Icon(
                    Icons.Default.Delete,
                    null
                )
            }
        }
    }
}

@Composable
private fun ExpandedArrowIcon(
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val targetRotation = if (isSelected) 180f else 0f
    val rotation: Float by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = tween()
    )
    Icon(
        Icons.Outlined.ArrowDropDown,
        null,
        Modifier
            .rotate(rotation)
            .then(modifier)
    )
}

/* Preview */
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun PreviewTaskRow(
    @PreviewParameter(TaskStatePreviewProvider::class) taskState: TaskState
) {
    TodoAppTheme {
        TaskRow(taskState, index = 0) { }
    }
}
