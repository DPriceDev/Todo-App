package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.ui.list.model.TaskState
import dev.dprice.productivity.todo.ui.components.RoundedButton
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import dev.dprice.productivity.todo.ui.theme.Yellow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskRow(
    task: TaskState,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    Card(
        elevation = 8.dp,
        onClick = onClicked,
        backgroundColor = MediumBlue,
        contentColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .animateContentSize()
            .padding(4.dp)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .background(
                        Yellow
                    )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = task.task.title
                        )
                        Text(
                            text = task.task.dateTime.asTaskDateString()
                        )
                    }

                    // Status icon, progress and tick and cross
                    Icon(
                        Icons.Outlined.Circle,
                        null,
                        modifier = androidx.compose.ui.Modifier
                            .size(48.dp)
                            .padding(12.dp)
                    )
                }
                if (task.isSelected) {
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
            }
        }
    }
}