package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TextColour
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.buttonDisabledColour
import dev.dprice.productivity.todo.ui.theme.buttonDisabledTextColour

@Composable
fun RoundedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(percent = 50),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = TextColour,
        disabledBackgroundColor = buttonDisabledColour,
        disabledContentColor = buttonDisabledTextColour
    ),
    contentPadding: PaddingValues = PaddingValues(
        vertical = 16.dp,
        horizontal = 48.dp
    ),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick,
        modifier,
        enabled,
        interactionSource,
        elevation,
        shape,
        border,
        colors,
        contentPadding,
        content
    )
}

enum class ButtonEnablement {
    ENABLED,
    LOADING,
    DISABLED
}

@Composable
fun RoundedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonEnablement: ButtonEnablement = ButtonEnablement.ENABLED,
    shape: Shape = RoundedCornerShape(percent = 50),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = TextColour,
        disabledBackgroundColor = buttonDisabledColour,
        disabledContentColor = buttonDisabledTextColour
    ),
    contentPadding: PaddingValues = PaddingValues(
        vertical = 16.dp,
        horizontal = 48.dp
    ),
) {
    Button(
        if(buttonEnablement == ButtonEnablement.ENABLED) onClick else ({ /* do nothing */ }),
        modifier,
        buttonEnablement != ButtonEnablement.DISABLED,
        interactionSource,
        elevation,
        shape,
        border,
        colors,
        contentPadding
    ) {
        Box(
            modifier = Modifier.height(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Crossfade(targetState = buttonEnablement) { enabledState ->
                when(enabledState) {
                    ButtonEnablement.LOADING -> LoadingDots()
                    else -> Text(text = text)
                }
            }

        }
    }
}

@Preview
@Composable
private fun PreviewRoundedButton() {
    TodoAppTheme() {
        RoundedButton(
            onClick = { /*TODO*/ }
        ) {
            Text("Example Text")
        }
    }
}