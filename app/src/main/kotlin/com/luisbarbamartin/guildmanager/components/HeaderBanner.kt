package com.luisbarbamartin.guildmanager.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun HeaderBanner(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    contentPadding: PaddingValues = PaddingValues(
        start = 16.dp,
        top = 12.dp,
        end = 48.dp,
        bottom = 12.dp
    )
) {
    val bannerColor = MaterialTheme.colorScheme.secondary
    val bannerTextColor = MaterialTheme.colorScheme.onSecondary

    Box(
        modifier = modifier
            .drawBehind {
                val pointWidth = 36.dp.toPx()

                val bannerPath = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width - pointWidth, 0f)
                    lineTo(size.width, size.height / 2f)
                    lineTo(size.width - pointWidth, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                drawPath(
                    path = bannerPath,
                    color = bannerColor
                )
            }
    ) {
        Text(
            text = text,
            style = textStyle,
            color = bannerTextColor,
            modifier = Modifier.padding(contentPadding)
        )
    }
}
