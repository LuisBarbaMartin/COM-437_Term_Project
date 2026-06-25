package com.luisbarbamartin.guildmanager.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luisbarbamartin.guildmanager.AppScreen
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme

@Composable
fun BottomNavBar(
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier,
    vertical: Boolean = false
) {
    val screens = listOf(
        AppScreen.Dashboard to "Home",
        AppScreen.GuildBank to "Bank",
        AppScreen.Members to "Guild",
        AppScreen.Quests to "Quest",
        AppScreen.Recruitment to "Recruit"
    )

    if (vertical) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .width(92.dp)
                .navigationBarsPadding()
        ) {
            screens.forEach { (screen, label) ->
                BottomNavTab(
                    label = label,
                    onClick = { onNavigate(screen) },
                    vertical = true,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            screens.forEach { (screen, label) ->
                BottomNavTab(
                    label = label,
                    onClick = { onNavigate(screen) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun BottomNavTab(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    vertical: Boolean = false
) {
    val tabColor = MaterialTheme.colorScheme.secondary
    val textColor = MaterialTheme.colorScheme.onSecondary
    val outlineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f)

    val tabShape = if (vertical) {
        RoundedCornerShape(
            topStart = 14.dp,
            topEnd = 0.dp,
            bottomStart = 14.dp,
            bottomEnd = 0.dp
        )
    } else {
        RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 14.dp,
            bottomEnd = 14.dp
        )
    }

    Surface(
        modifier = modifier,
        shape = tabShape,
        color = tabColor,
        contentColor = textColor,
        shadowElevation = 60.dp,
        tonalElevation = 20.dp
    ) {
        TextButton(
            onClick = onClick,
            shape = tabShape,
            contentPadding = if (vertical) {
                PaddingValues(horizontal = 4.dp, vertical = 8.dp)
            } else {
                PaddingValues(vertical = 12.dp)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                maxLines = 1,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    COM437TermProjectTheme(
        darkTheme = false,
    ) {
        BottomNavBar(onNavigate = {})
    }
}
