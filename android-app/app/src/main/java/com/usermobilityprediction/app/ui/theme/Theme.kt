package com.usermobilityprediction.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SafePathDarkColors = darkColorScheme(
    primary = PrimaryBlue,
    secondary = Cyan,
    tertiary = Purple,

    background = Background,
    surface = Surface,
    surfaceVariant = SurfaceVariant,

    onPrimary = TextPrimary,
    onSecondary = TextPrimary,
    onTertiary = TextPrimary,

    onBackground = TextPrimary,
    onSurface = TextPrimary,

    error = Error,
    onError = TextPrimary
)

private val SafePathLightColors = lightColorScheme(
    primary = PrimaryBlue,
    secondary = Cyan,
    tertiary = Purple
)

@Composable
fun SafePathTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        SafePathDarkColors
    } else {
        SafePathLightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}