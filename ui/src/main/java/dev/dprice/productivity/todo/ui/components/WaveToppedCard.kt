package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.shapes.waveToppedShape
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun WaveToppedCard(
    modifier: Modifier = Modifier,
    waveOffset: Float = 0f,
    waveHeight: Dp = 8.dp,
    frequency: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    with(LocalDensity.current) {
        Card(
            modifier = modifier,
            shape = waveToppedShape(
                height = waveHeight.toPx(),
                offset = waveOffset,
                frequency = frequency
            ),
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            border = border,
            elevation = elevation,
            content = content
        )
//        ) {
//            Box(
//                modifier = Modifier.padding(top = waveHeight)
//            ) {
//                content()
//            }
//        }
    }
}

@Preview
@Composable
fun PreviewFluidTopCard() {
    TodoAppTheme {
        WaveToppedCard {
            Text(
                text = "Example Body",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}