package dev.dprice.productivity.todo.auth.feature.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TitleBlock(
    colour: Color,
    title: String = "Create Account"
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .height(128.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            color = colour,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
