package com.luisbarbamartin.guildmanager.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.AppScreen
import com.luisbarbamartin.guildmanager.components.BottomNavBar
import com.luisbarbamartin.guildmanager.components.TopStatusBar

@Composable
fun GuildBankScreen(
    onNavigate: (AppScreen) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopStatusBar()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Guild Bank",
                style = MaterialTheme.typography.headlineLarge
            )

            Text("Guild items/currency will appear here.")
        }

        BottomNavBar(
            onNavigate = onNavigate
        )
    }
}