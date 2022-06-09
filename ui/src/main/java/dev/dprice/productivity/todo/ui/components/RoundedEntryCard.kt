package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.dprice.productivity.todo.ui.theme.*

data class EntryField(
    val value: String = "",
    val hasFocus: Boolean = false,
    val isValid: Boolean = true,
    val shouldValidate: Boolean = false,
    val hintText: String = "",
    val errorText: String? = null,
    val icon: ImageVector? = null,
    val contentDescription: String? = null,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val maxLength: Int = Int.MAX_VALUE,
    val imeAction: ImeAction = ImeAction.Next
)

@Composable
fun RoundedEntryCard(
    entry: EntryField,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    onImeAction: () -> Unit = { },
    onTextChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = entry.value,
            onValueChange = onTextChanged,
            modifier = Modifier.fillMaxWidth(),
            isError = !entry.isValid && entry.shouldValidate,
            singleLine = true,
            textStyle = textStyle,
            shape = RoundedCornerShape(percent = 50),
            visualTransformation = entry.visualTransformation,
            keyboardActions = KeyboardActions { onImeAction() },
            keyboardOptions = KeyboardOptions(imeAction = entry.imeAction),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = if (!entry.isValid && entry.shouldValidate) ErrorBackgroundColour else TextBackground,
                textColor = TextColour,
                leadingIconColor = TextColour,
                errorBorderColor = ErrorTextColour
            ),
            placeholder = {
                Text(
                    text = entry.hintText,
                    color = TextHintColour
                )
            },
            leadingIcon = entry.icon?.let {
                {
                    Icon(
                        imageVector = entry.icon,
                        contentDescription = entry.contentDescription,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        )

        if (!entry.isValid && entry.shouldValidate && entry.errorText != null) {
            Text(
                text = entry.errorText,
                color = ErrorTextColour,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(bottom = 8.dp, start = 24.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRoundedEntryCard() {
    TodoAppTheme {
        RoundedEntryCard(entry = EntryField(value = "test")) { }
    }
}