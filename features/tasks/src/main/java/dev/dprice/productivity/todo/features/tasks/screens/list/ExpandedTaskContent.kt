package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.list.model.TaskState
import dev.dprice.productivity.todo.ui.components.RoundedButton

@Composable
fun ExpandedTaskContent(
    task: TaskState,
    onCompleteTaskClick: () -> Unit,
    onDeleteClicked: () -> Unit
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
                onClick = { onDeleteClicked() },
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