package com.luisbarbamartin.guildmanager.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.luisbarbamartin.guildmanager.AppScreen

@Composable
fun AdaptiveScreenScaffold(
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                content = content
            )

            BottomNavBar(
                onNavigate = onNavigate,
                vertical = true
            )
        }
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.weight(1f),
                content = content
            )

            BottomNavBar(onNavigate = onNavigate)
        }
    }
}
