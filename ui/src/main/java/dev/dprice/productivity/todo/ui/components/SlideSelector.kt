package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

// todo: Animate slider between items
@Composable
fun SlideSelector(
    vararg items: String,
    selected: Int,
    modifier: Modifier = Modifier,
    onSelected: (Int) -> Unit
) {
    Surface(
        color = Color.Black.copy(alpha = 0.32f),
        shape = RoundedCornerShape(percent = 50),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
            ) {
                items.forEachIndexed { index, title ->
                    if (index == selected) {
                        Card(
                            backgroundColor = Yellow,
                            shape = RoundedCornerShape(percent = 50),
                        ) {
                            Text(
                                text = title,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(percent = 50))
                                .clickable { onSelected(index) }
                        ) {
                            Text(
                                text = title,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                                style = MaterialTheme.typography.body1,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSlideSelector() {
    TodoAppTheme {
        SlideSelector(
            "New Task",
            "New Group",
            selected = 0,
            onSelected = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}
