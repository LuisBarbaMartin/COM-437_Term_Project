package com.example.com_437termproject.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.com_437termproject.AppScreen

@Composable
fun BottomNavBar(
    onNavigate: (AppScreen) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                onNavigate(AppScreen.Dashboard)
            }
        ) {
            Text("Dashboard")
        }

        Button(
            onClick = {
                onNavigate(AppScreen.GuildBank)
            }
        ) {
            Text("Bank")
        }

        Button(
            onClick = {
                onNavigate(AppScreen.Members)
            }
        ) {
            Text("Members")
        }

        Button(
            onClick = {
                onNavigate(AppScreen.Quests)
            }
        ) {
            Text("Quests")
        }

        Button(
            onClick = {
                onNavigate(AppScreen.Recruitment)
            }
        ) {
            Text("Recruit")
        }
    }
}