package com.luisbarbamartin.guildmanager.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.managers.GameManager


@Composable
fun TopStatusBar(
    onMenuClick: () -> Unit = {}
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val verticalPadding = if (isLandscape) 2.dp else 8.dp
    val bannerPadding = if (isLandscape) {
        PaddingValues(start = 10.dp, top = 3.dp, end = 28.dp, bottom = 3.dp)
    } else {
        PaddingValues(start = 12.dp, top = 6.dp, end = 36.dp, bottom = 6.dp)
    }
    val titleStyle = if (isLandscape) {
        MaterialTheme.typography.titleSmall
    } else {
        MaterialTheme.typography.titleMedium
    }
    val statusStyle = if (isLandscape) {
        MaterialTheme.typography.bodySmall
    } else {
        MaterialTheme.typography.bodyMedium
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 4.dp, vertical = verticalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Navigation Menu",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            HeaderBanner(
                text = GameManager.guildName,
                modifier = Modifier.padding(end = 20.dp),
                textStyle = titleStyle,
                contentPadding = bannerPadding
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(if (isLandscape) 8.dp else 16.dp),
            modifier = Modifier.padding(end = 12.dp)
        ) {
            Text(
                text = "fame: ${GameManager.fame}",
                style = statusStyle
            )

            Text(
                text = "gold: ${GameManager.gold}",
                style = statusStyle
            )
        }
    }
}
