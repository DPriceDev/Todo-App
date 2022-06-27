package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.animation.animateContentSize
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
import androidx.compose.runtime.mutableStateOf
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
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.features.tasks.screens.list.preview.TaskPreviewProvider
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
    task: Task,
    isSelected: Boolean,
    index: Int,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onCompleteTaskClick: () -> Unit = { },
    onClicked: () -> Unit = { },
    expandableContent: @Composable () -> Unit
) {
    val isSwiped = remember { mutableStateOf(false) }
    val swipeState = rememberSwipeableState(initialValue = isSwiped.value)
    LaunchedEffect(key1 = swipeState.currentValue) {
        isSwiped.value = swipeState.currentValue
    }

    LaunchedEffect(key1 = isSwiped.value) {
        if (isSwiped.value) {
            swipeState.animateTo(false)
            onCompleteTaskClick()
        } else {
            swipeState.snapTo(false)
        }
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .focusRequester(focusRequester)
            .focusable()
            .height(IntrinsicSize.Min)
            .then(modifier)
    ) {
        if (swipeState.offset.value < 0f) {
            BehindTaskCard(isComplete = task.isCompleted)
        }

        val maxSwipe = with(LocalDensity.current) { 256.dp.toPx() }
        val canSwipe = !swipeState.currentValue && !isSelected

        SwipeableCard(
            swipeState = swipeState,
            swipingEnabled = canSwipe,
            enabled = swipeState.offset.value == 0f,
            // todo: fix this, jumps around :/
            // maybe animate content size internally?
            modifier = Modifier.animateContentSize(
                animationSpec = tween()
            ),
            onClick = {
                if (!isSelected) focusRequester.requestFocus()
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
                isSelected = isSelected,
                expandableContent = expandableContent
            )
        }
    }
}

@Composable
private fun TaskRowContent(
    task: Task,
    isSelected: Boolean,
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
                    Text(text = task.title)
                    Text(text = task.finishDate.asTaskDateString())
                }

                StatusIcon(
                    task.isCompleted,
                    index = index,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            if (isSelected) {
                expandableContent()
            }

            ExpandedArrowIcon(
                isSelected = isSelected,
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
@Preview
@Composable
private fun PreviewTaskRow(
    @PreviewParameter(TaskPreviewProvider::class) task: Task
) {
    TodoAppTheme {
        TaskRow(task, index = 0, isSelected = false) { }
    }
}

@Preview
@Composable
private fun PreviewSelectedTaskRow(
    @PreviewParameter(TaskPreviewProvider::class) task: Task
) {
    TodoAppTheme {
        TaskRow(task, index = 0, isSelected = true) { }
    }
}
