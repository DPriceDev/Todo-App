package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskState
import dev.dprice.productivity.todo.features.tasks.ui.list.preview.TaskStatePreviewProvider
import dev.dprice.productivity.todo.platform.util.asTaskDateString
import dev.dprice.productivity.todo.ui.components.RoundedButton
import dev.dprice.productivity.todo.ui.components.VerticalDivider
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskRow(
    task: TaskState,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    Card(
        elevation = 8.dp,
        onClick = {
            if(!task.isSelected) focusRequester.requestFocus()
            onClicked()
        },
        backgroundColor = MediumBlue,
        contentColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusable()
            .animateContentSize()
            .padding(4.dp)
            .then(modifier)
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
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                if (task.isSelected) {
                    ExpandedTaskContent(task)
                }

                ExpandedArrowIcon(
                    isSelected = task.isSelected,
                    modifier = modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun TaskStatusIcon(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(end = 12.dp)
            .then(modifier)
    ) {
        Icon(
            Icons.Outlined.Circle,
            null,
            Modifier.size(24.dp)
        )
    }
}

@Composable
private fun ExpandedTaskContent(task: TaskState) {
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
        ) {
            RoundedButton(
                onClick = { /*TODO*/ },
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
@Preview
@Composable
private fun PreviewTaskRow(
    @PreviewParameter(TaskStatePreviewProvider::class) taskState: TaskState
) {
    TodoAppTheme {
        TaskRow(taskState) { }
    }
}
