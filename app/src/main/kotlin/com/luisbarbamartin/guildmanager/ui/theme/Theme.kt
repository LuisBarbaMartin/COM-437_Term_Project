package com.luisbarbamartin.guildmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GuildGold,
    onPrimary = InkBlack,
    primaryContainer = DarkLeather,
    onPrimaryContainer = GuildGold,

    secondary = LeatherBrown,
    onSecondary = Color.White,
    secondaryContainer = DarkLeather,
    onSecondaryContainer = Parchment,

    tertiary = Burgundy,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF400010),
    onTertiaryContainer = Burgundy,

    background = InkBlack,
    onBackground = LightParchment,

    surface = DarkLeather,
    onSurface = LightParchment,

    surfaceVariant = DarkSlate,
    onSurfaceVariant = Parchment,

    error = ErrorRed,
    onError = Color.White,

    outline = GuildGold,
    outlineVariant = LeatherBrown
)

private val LightColorScheme = lightColorScheme(
    primary = GuildGold,
    onPrimary = InkBlack,
    primaryContainer = MutedParchment,
    onPrimaryContainer = DarkLeather,

    secondary = LeatherBrown,
    onSecondary = Color.White,
    secondaryContainer = MutedParchment,
    onSecondaryContainer = DarkLeather,

    tertiary = Burgundy,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDADE),
    onTertiaryContainer = Burgundy,

    background = Parchment,
    onBackground = InkBlack,

    surface = LightParchment,
    onSurface = InkBlack,

    surfaceVariant = MutedParchment,
    onSurfaceVariant = DarkLeather,

    error = ErrorRed,
    onError = Color.White,

    outline = LeatherBrown,
    outlineVariant = GuildGold
)

@Composable
fun COM437TermProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // dynamicColor removed to ensure total control over app appearance
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
