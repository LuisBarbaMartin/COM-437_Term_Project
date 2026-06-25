package com.luisbarbamartin.guildmanager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.data.NewGameData
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.managers.GuildNotificationManager
import com.luisbarbamartin.guildmanager.managers.QuestAlarmScheduler
import com.luisbarbamartin.guildmanager.managers.SaveManager
import com.luisbarbamartin.guildmanager.screens.DashboardScreen
import com.luisbarbamartin.guildmanager.screens.EquipmentScreen
import com.luisbarbamartin.guildmanager.screens.GuildBankScreen
import com.luisbarbamartin.guildmanager.screens.MembersScreen
import com.luisbarbamartin.guildmanager.screens.QuestsScreen
import com.luisbarbamartin.guildmanager.screens.RecruitmentScreen
import com.luisbarbamartin.guildmanager.screens.SettingsScreen
import com.luisbarbamartin.guildmanager.screens.StartScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GuildManagerApp(
    initialScreen: AppScreen = AppScreen.Start
) {
    val context = LocalContext.current
    val saveManager = remember { SaveManager(context) }
    val notificationManager = remember { GuildNotificationManager(context) }
    val questAlarmScheduler = remember { QuestAlarmScheduler(context) }
    var currentScreen by rememberSaveable { mutableStateOf(initialScreen) }
    var selectedEquipmentMemberId by rememberSaveable { mutableStateOf<Int?>(null) }
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)

            if (
                GameManager.completeFinishedQuests(
                    onQuestCompleted = { questTitle, isSuccess ->
                        notificationManager.showQuestCompletedNotification(
                            questTitle = questTitle,
                            isSuccess = isSuccess
                        )
                    }
                )
            ) {
                saveManager.saveGame(GameManager.toGameState())
                questAlarmScheduler.scheduleNextQuestCompletion()
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        if (currentScreen == AppScreen.Start) {
            StartScreen(
                onCreateGuildClick = { guildName ->
                    GameManager.fromGameState(
                        NewGameData.getInitialState(guildName)
                    )
                    saveManager.saveGame(GameManager.toGameState())
                    questAlarmScheduler.scheduleNextQuestCompletion()
                    currentScreen = AppScreen.Dashboard
                },
                onDemoClick = {
                    GameManager.fromGameState(DemoData.getInitialState())
                    saveManager.saveGame(GameManager.toGameState())
                    questAlarmScheduler.scheduleNextQuestCompletion()
                    currentScreen = AppScreen.Dashboard
                }
            )
        } else {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Text(
                            text = "Guild Menu",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                        HorizontalDivider()
                        
                        NavigationDrawerItem(
                            label = { Text("Dashboard") },
                            selected = currentScreen == AppScreen.Dashboard,
                            onClick = {
                                currentScreen = AppScreen.Dashboard
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        NavigationDrawerItem(
                            label = { Text("Guild Roster") },
                            selected = currentScreen == AppScreen.Members,
                            onClick = {
                                currentScreen = AppScreen.Members
                                selectedEquipmentMemberId = null
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        NavigationDrawerItem(
                            label = { Text("Quest Board") },
                            selected = currentScreen == AppScreen.Quests,
                            onClick = {
                                currentScreen = AppScreen.Quests
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        NavigationDrawerItem(
                            label = { Text("Recruitment") },
                            selected = currentScreen == AppScreen.Recruitment,
                            onClick = {
                                currentScreen = AppScreen.Recruitment
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        NavigationDrawerItem(
                            label = { Text("Guild Bank") },
                            selected = currentScreen == AppScreen.GuildBank,
                            onClick = {
                                currentScreen = AppScreen.GuildBank
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        
                        NavigationDrawerItem(
                            label = { Text("Return to Start") },
                            selected = false,
                            onClick = {
                                currentScreen = AppScreen.Start
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        NavigationDrawerItem(
                            label = { Text("Save Game") },
                            selected = false,
                            onClick = {
                                saveManager.saveGame(GameManager.toGameState())
                                questAlarmScheduler.scheduleNextQuestCompletion()
                                Toast.makeText(context, "Game saved", Toast.LENGTH_SHORT).show()
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        NavigationDrawerItem(
                            label = { Text("Settings") },
                            selected = false,
                            onClick = {
                                currentScreen = AppScreen.Settings
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            ) {
                when (currentScreen) {
                    AppScreen.Dashboard -> DashboardScreen(
                        drawerState = drawerState,
                        onNavigate = { selectedScreen ->
                            currentScreen = selectedScreen
                        }
                    )

                    AppScreen.GuildBank -> GuildBankScreen(
                        drawerState = drawerState,
                        onNavigate = { selectedScreen ->
                            currentScreen = selectedScreen
                        }
                    )

                    AppScreen.Members -> MembersScreen(
                        drawerState = drawerState,
                        onNavigate = { selectedScreen ->
                            currentScreen = selectedScreen
                        },
                        onManageEquipment = { memberId ->
                            selectedEquipmentMemberId = memberId
                            currentScreen = AppScreen.Equipment
                        }
                    )

                    AppScreen.Equipment -> EquipmentScreen(
                        memberId = selectedEquipmentMemberId,
                        drawerState = drawerState,
                        onNavigate = { selectedScreen ->
                            if (selectedScreen != AppScreen.Equipment) {
                                selectedEquipmentMemberId = null
                            }
                            currentScreen = selectedScreen
                        }
                    )

                    AppScreen.Quests -> QuestsScreen(
                        drawerState = drawerState,
                        onNavigate = { selectedScreen ->
                            currentScreen = selectedScreen
                        }
                    )

                    AppScreen.Recruitment -> RecruitmentScreen(
                        drawerState = drawerState,
                        onNavigate = { selectedScreen ->
                            currentScreen = selectedScreen
                        }
                    )

                    AppScreen.Settings -> SettingsScreen(
                        drawerState = drawerState,
                        onNavigate = { selectedScreen ->
                            currentScreen = selectedScreen
                        }
                    )
                    else -> {} // covered by StartScreen if
                }
            }
        }
    }
}
