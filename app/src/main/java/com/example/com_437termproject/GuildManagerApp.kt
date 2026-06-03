package com.example.com_437termproject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.com_437termproject.screens.DashboardScreen
import com.example.com_437termproject.screens.MembersScreen
import com.example.com_437termproject.screens.QuestsScreen
import com.example.com_437termproject.screens.RecruitmentScreen
import com.example.com_437termproject.screens.StartScreen
import com.example.com_437termproject.screens.GuildBankScreen



// controls which screen the app displays
@Composable
fun GuildManagerApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.Start) }

    when (currentScreen) {
        AppScreen.Start -> StartScreen(
            onStartClick = {
                currentScreen = AppScreen.Dashboard
            }
        )

        AppScreen.Dashboard -> DashboardScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )

        AppScreen.GuildBank -> GuildBankScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )

        AppScreen.Members -> MembersScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )

        AppScreen.Quests -> QuestsScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )

        AppScreen.Recruitment -> RecruitmentScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )
    }
}