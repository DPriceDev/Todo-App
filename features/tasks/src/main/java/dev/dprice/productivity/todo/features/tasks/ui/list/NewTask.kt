package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.RoundedButton

@Composable
fun NewTask() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Test")
        Text("test 2")
        RoundedButton(
            text = "Add Task",
            onClick = { /*TODO*/ }
        )
    }
}