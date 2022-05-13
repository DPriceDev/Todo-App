package dev.dprice.productivity.todo.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.dprice.productivity.todo.ui.R

val signikaNegative = FontFamily(
    Font(R.font.signika_negative_light, weight = FontWeight.Light),
    Font(R.font.signika_negative_medium, weight = FontWeight.Medium),
    Font(R.font.signika_negative_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.signika_negative_bold, weight = FontWeight.Bold),
    Font(R.font.signika_negative_regular, weight = FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = signikaNegative,
    body1 = TextStyle(
        fontFamily = signikaNegative,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = signikaNegative,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
    ),
    h2 = TextStyle(
        fontFamily = signikaNegative,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    ),
    h3 = TextStyle(
        fontFamily = signikaNegative,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    ),
    button = TextStyle(
        fontFamily = signikaNegative,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    ),
    /*
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
