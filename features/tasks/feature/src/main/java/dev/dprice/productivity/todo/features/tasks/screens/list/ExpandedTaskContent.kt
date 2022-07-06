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
import dev.dprice.productivity.todo.features.tasks.data.model.Task
import dev.dprice.productivity.todo.ui.components.RoundedButton

@Composable
fun ExpandedTaskContent(
    task: Task,
    onCompleteTaskClick: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (task.details.isNotEmpty()) {
            Text(text = "Description")
            Text(text = task.details)
        }

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            mapOf(
                Icons.Default.Done to "Complete",
                Icons.Default.Edit to "Edit",
                Icons.Default.Delete to "Delete",
            ).forEach { (icon, title) ->
                RoundedButton(
                    onClick = {
                        when(title) {
                            "Complete" -> onCompleteTaskClick()
                            "Delete" -> onDeleteClicked()
                            else -> { }
                        }
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}