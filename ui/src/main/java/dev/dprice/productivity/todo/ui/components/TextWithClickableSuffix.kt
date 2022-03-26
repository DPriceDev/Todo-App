package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun TextWithClickableSuffix(
    text: String,
    suffixText: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.body1,
    textColour: Color = MaterialTheme.colors.onBackground,
    clickableColour: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = textColour)) {
            append(text)
        }

        pushStringAnnotation(tag = "suffixTag", annotation = "suffixTag")
        withStyle(style = SpanStyle(color = clickableColour)) {
            append(suffixText)
        }
    }

    ClickableText(
        text = annotatedText,
        modifier = modifier,
        style = style,
        onClick = { offset ->
            annotatedText.getStringAnnotations("suffixTag", offset, offset)
                .firstOrNull()
                ?.let {
                    onClick()
                }
        }
    )
}