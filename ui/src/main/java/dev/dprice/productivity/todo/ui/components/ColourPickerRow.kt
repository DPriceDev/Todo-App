package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.Yellow

@Composable
fun ColourPickerRow(
    colour: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val scale by rememberInfiniteTransition().animateFloat(
        initialValue = 1f,
        targetValue = 0.98f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Group Colour",
            modifier.padding(bottom = 12.dp),
            style = MaterialTheme.typography.body1,
            color = Color.White
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 50))
                .background(color = Color.White)
                .border(
                    width = 2.dp,
                    color = Yellow,
                    shape = RoundedCornerShape(percent = 50)
                )
                .size(48.dp)
                .scale(scale)
        )
    }
}