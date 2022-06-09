package dev.dprice.productivity.todo.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.R
import dev.dprice.productivity.todo.ui.theme.ErrorTextColour
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun WarningMessage(
    message: String,
    modifier: Modifier = Modifier,
    @StringRes titleId: Int = R.string.error_title
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = ErrorTextColour,
        contentColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = titleId),
                style = MaterialTheme.typography.h3.copy(
                    textAlign = TextAlign.Center
                )
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.body1.copy(
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWarningMessage() {
    TodoAppTheme {
        WarningMessage(
            message = LoremIpsum(30).values.joinToString(" "),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
