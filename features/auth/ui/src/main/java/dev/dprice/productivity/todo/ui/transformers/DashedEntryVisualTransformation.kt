package dev.dprice.productivity.todo.ui.transformers

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import dev.dprice.productivity.todo.ui.theme.TextHintColour

class DashedEntryVisualTransformation(
    private val maxLength: Int,
    private val emptyCharacter: Char = '‒'
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val newText = buildString {
            append(text.text)
            repeat(maxLength - text.length) {
                append(emptyCharacter)
            }
        }.toList().joinToString(" ")

        return TransformedText(
            buildAnnotatedString {
                append(newText)
                addStyle(
                    SpanStyle(color = TextHintColour),
                    (text.length + text.length - 1).coerceAtLeast(0),
                    maxLength + maxLength - 1
                )
            },
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int = minOf(
                    offset + offset,
                    maxLength + maxLength - 1
                )
                override fun transformedToOriginal(offset: Int): Int = minOf(
                    offset - (offset / 2),
                    maxLength
                )
            }
        )
    }
}
