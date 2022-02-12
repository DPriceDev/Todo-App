package dev.dprice.productivity.todo.auth.feature.ui.landing

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.RoundedButton
import dev.dprice.productivity.todo.ui.components.WavePosition
import dev.dprice.productivity.todo.ui.components.WavyScaffold
import dev.dprice.productivity.todo.ui.theme.*

@Composable
fun AuthLanding(
    goToSignUp: () -> Unit,
    goToSignIn: () -> Unit
) {

    // todo need to make scaffold with variable wave height
    // todo or use offset padding?
    WavyScaffold(
        topContent = { height ->
            Box(
                modifier = Modifier.height(height + 64.dp)
            ) {
                Circles()
            }
        },
        wavePosition = WavePosition.Wrap,
        waveHeight = 48.dp,
        waveFrequency = 0.3f
    ) { topPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = topPadding + 8.dp, bottom = 32.dp)
        ) {
            TextBlock(goToSignUp, goToSignIn)
        }
    }
}

@Composable
private fun TextBlock(
    goToSignUp: () -> Unit,
    goToSignIn: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Text(
            text = "Much Todo About Nothing",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            color = Yellow
        )

        Text(
            text = """
                Some sort of description here...
                Some sort of description here...
            """.trimIndent(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            color = TextBackground
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
        ) {
            RoundedButton(onClick = goToSignIn) {
                Text(text = "Sign In")
            }

            RoundedButton(onClick = goToSignUp) {
                Text(text = "Sign Up")
            }
        }
    }
}

@Composable
private fun Circles(

) {

    val cross = rememberVectorPainter(image = Icons.Rounded.Clear) to incompleteColour
    val progress = rememberVectorPainter(image = Icons.Rounded.MoreHoriz) to inProgressColour
    val complete = rememberVectorPainter(image = Icons.Rounded.Check) to completeColour

    val infinite = rememberInfiniteTransition()
    val anim = infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 15_000,
                easing = LinearEasing
            )
        )
    )

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val diameter = size.width / 4.5f
        val width = size.width - diameter
        val height = size.height - diameter

        val circles = listOf(
            cross to (Offset(width * 0.1f, height * 0.05f) to 10f + anim.value),
            progress to (Offset(width * 0.4f, height * 0.1f) to 20f - anim.value),
            complete to (Offset(width * 0.8f, height * 0.07f) to 30f + anim.value),
            complete to (Offset(width * 0.15f, height * 0.15f) to 10f + anim.value),
            cross to (Offset(width * 0.55f, height * 0.2f) to 20f - anim.value),
            progress to (Offset(width * 0.8f, height * 0.24f) to 30f - anim.value),
            complete to (Offset(width * 0.2f, height * 0.4f) to 10f + anim.value),
            cross to (Offset(width * 0.7f, height * 0.32f) to 20f + anim.value),
            progress to (Offset(width * 0.9f, height * 0.55f) to 30f - anim.value),
        )

        circles.map { (graphic, transform) ->
            val (painter, colour) = graphic
            val (offset, rotation) = transform

            translate(
                top = offset.y,
                left = offset.x
            ) {
                rotate(
                    degrees = rotation,
                    Offset(diameter / 2, diameter / 2)
                ) {
                    circleIcon(
                        painter,
                        colour,
                        diameter
                    )
                }
            }
        }
    }
}

private fun DrawScope.circleIcon(
    painter: VectorPainter,
    backgroundColour: Color,
    diameter: Float
) {
    val radius = diameter / 2
    drawCircle(
        color = backgroundColour,
        radius = radius,
        center = Offset(radius, radius)
    )

    val iconSize = diameter * 0.75f
    val offset = diameter * 0.125f

    translate(
        left = offset,
        top = offset
    ) {
        with(painter) {
            draw(
                size = Size(iconSize, iconSize),
                colorFilter = ColorFilter.tint(
                    color = Color.White
                )
            )
        }

    }
}

//
//@Composable
//private fun FloatingCircles() {
//    BoxWithConstraints(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        val diameter = maxWidth / 5
//        //FloatingCircle(size = diameter)
//
//        FloatingCircle(
//            size = diameter,
//            colour = incompleteColour,
//            icon = Icons.Rounded.Clear
//        )
//
//        FloatingCircle(
//            size = diameter,
//            colour = inProgressColour,
//            icon = Icons.Rounded.MoreHoriz
//        )
//    }
//}
//
//@Composable
//private fun FloatingCircle(
//    size: Dp,
//    modifier: Modifier = Modifier,
//    icon: ImageVector = Icons.Rounded.Check,
//    colour: Color = completeColour
//) {
//    Surface(
//        shape = RoundedCornerShape(percent = 50),
//        color = colour,
//        elevation = 8.dp,
//        modifier = Modifier
//            .size(size)
//            .then(modifier)
//    ) {
//        Icon(
//            imageVector = icon,
//            modifier = Modifier.padding(size / 6),
//            tint = Color.White,
//            contentDescription = null
//        )
//    }
//}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewAuthLanding() {
    TodoAppTheme {
        AuthLanding({ }) { }
    }
}