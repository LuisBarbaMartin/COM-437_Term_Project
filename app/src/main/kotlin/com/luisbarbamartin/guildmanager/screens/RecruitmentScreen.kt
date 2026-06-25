package com.luisbarbamartin.guildmanager.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.AppScreen
import com.luisbarbamartin.guildmanager.components.AdaptiveScreenScaffold
import com.luisbarbamartin.guildmanager.components.GuildMemberCard
import com.luisbarbamartin.guildmanager.components.SortChipRow
import com.luisbarbamartin.guildmanager.components.TopStatusBar
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.managers.SaveManager
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme
import kotlinx.coroutines.launch

enum class RecruitSort {
    Name, Job, Level
}

@Composable
fun RecruitmentScreen(
    drawerState: DrawerState,
    onNavigate: (AppScreen) -> Unit
) {
    val context = LocalContext.current
    val saveManager = remember { SaveManager(context) }
    val scope = rememberCoroutineScope()

    var selectedRecruitId by remember { mutableStateOf<Int?>(null) }
    var currentSort by remember { mutableStateOf(RecruitSort.Name) }

    val sortedRecruits = remember(currentSort, GameManager.recruits.size) {
        when (currentSort) {
            RecruitSort.Name -> GameManager.recruits.sortedBy { it.name }
            RecruitSort.Job -> GameManager.recruits.sortedBy { it.jobClass.name }
            RecruitSort.Level -> GameManager.recruits.sortedByDescending { it.level }
        }
    }
    // TODO: Show an empty state if there are no recruits available.

    AdaptiveScreenScaffold(onNavigate = onNavigate) {
        TopStatusBar(
            onMenuClick = {
                scope.launch { drawerState.open() }
            }
        )

        SortChipRow(
            label = "Sort recruits by:",
            options = RecruitSort.entries,
            selectedOption = currentSort,
            optionLabel = { it.name },
            onOptionSelected = { currentSort = it }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(sortedRecruits) { recruit ->
                GuildMemberCard(
                    member = recruit,
                    isSelected = selectedRecruitId == recruit.id,
                    onClick = {
                        selectedRecruitId = if (selectedRecruitId == recruit.id) null else recruit.id
                    }
                )

                if (selectedRecruitId == recruit.id) {
                    RecruitHiringPanel(
                        recruit = recruit,
                        onHireClick = {
                            GameManager.recruits.remove(recruit)
                            GameManager.guildMembers.add(recruit)
                            GameManager.addLog("${recruit.name} joined the guild.")
                            
                            // Auto-save after hiring
                            saveManager.saveGame(GameManager.toGameState())

                            selectedRecruitId = null
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RecruitHiringPanel(
    recruit: GuildMember,
    onHireClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Hire ${recruit.name}?",
                style = MaterialTheme.typography.titleMedium
            )
            
            Text(
                text = "This adventurer is ready to join your guild and take on quests.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(
                onClick = onHireClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to Guild")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecruitmentScreenPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        GameManager.fromGameState(DemoData.getInitialState())
        RecruitmentScreen(
            drawerState = rememberDrawerState(DrawerValue.Closed),
            onNavigate = {}
        )
    }
}
