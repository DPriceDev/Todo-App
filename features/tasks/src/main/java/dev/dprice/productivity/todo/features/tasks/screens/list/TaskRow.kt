package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskState
import dev.dprice.productivity.todo.features.tasks.screens.list.preview.TaskStatePreviewProvider
import dev.dprice.productivity.todo.platform.util.asTaskDateString
import dev.dprice.productivity.todo.ui.components.ExpandedArrowIcon
import dev.dprice.productivity.todo.ui.components.StatusIcon
import dev.dprice.productivity.todo.ui.components.SwipeableCard
import dev.dprice.productivity.todo.ui.components.VerticalDivider
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.completeColour
import dev.dprice.productivity.todo.ui.theme.inProgressColour

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskRow(
    task: TaskState,
    index: Int,
    modifier: Modifier = Modifier,
    swipeState: SwipeableState<Boolean> = rememberSwipeableState(initialValue = task.isSwiped),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onCompleteTaskClick: () -> Unit = { },
    onSwipeTask: () -> Unit = { },
    onClicked: () -> Unit = { },
    expandableContent: @Composable () -> Unit
) {
    val visibility: Float by animateFloatAsState(
        targetValue = if (task.isSwiped) 0f else 1f,
        animationSpec = tween(200),
        finishedListener = { if (it == 0f) onCompleteTaskClick() }
    )

    LaunchedEffect(key1 = swipeState.currentValue) {
        if (swipeState.currentValue) onSwipeTask()
    }

    LaunchedEffect(key1 = task.isSwiped) {
        if (!task.isSwiped) {
            swipeState.snapTo(false)
        } else {
            swipeState.animateTo(false)
            onCompleteTaskClick()
        }
    }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .focusRequester(focusRequester)
            .focusable()
            .height(IntrinsicSize.Min)
            //.alpha(visibility)
            .then(modifier)
    ) {
        if (swipeState.offset.value < 0f) {
            BehindTaskCard(isComplete = task.task.isComplete)
        }

        val maxSwipe = with(LocalDensity.current) { 256.dp.toPx() }
        val canSwipe = !swipeState.currentValue && !task.isSelected

        SwipeableCard(
            swipeState = swipeState,
            swipingEnabled = canSwipe,
            enabled = swipeState.offset.value == 0f,
            //modifier = Modifier.animateContentSize(),
            onClick = {
                if (!task.isSelected) focusRequester.requestFocus()
                onClicked()
            },
            anchors = mapOf(
                -maxSwipe to true,
                0f to false
            )
        ) {
            TaskRowContent(
                task = task,
                index = index,
                expandableContent = expandableContent
            )
        }
    }
}

@Composable
private fun TaskRowContent(
    task: TaskState,
    index: Int,
    modifier: Modifier = Modifier,
    expandableContent: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .then(modifier),
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

                StatusIcon(
                    task.task.isComplete,
                    index = index,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            if (task.isSelected) {
                expandableContent()

            }

            ExpandedArrowIcon(
                isSelected = task.isSelected,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun BehindTaskCard(
    isComplete: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 4.dp,
        contentColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .then(modifier),
        backgroundColor = if (!isComplete) completeColour else inProgressColour
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = if (!isComplete) "Task Complete!" else "Task Resumed",
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                if (!isComplete) Icons.Default.Check else Icons.Default.MoreHoriz,
                null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .padding(4.dp)
            )
        }
    }
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
