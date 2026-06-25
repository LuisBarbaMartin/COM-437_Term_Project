package com.luisbarbamartin.guildmanager.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.AppScreen
import com.luisbarbamartin.guildmanager.components.ActiveQuestList
import com.luisbarbamartin.guildmanager.components.AdaptiveScreenScaffold
import com.luisbarbamartin.guildmanager.components.MemberSelectionPanel
import com.luisbarbamartin.guildmanager.components.QuestCard
import com.luisbarbamartin.guildmanager.components.QuestDetailsPanel
import com.luisbarbamartin.guildmanager.components.SortChipRow
import com.luisbarbamartin.guildmanager.components.TopStatusBar
import com.luisbarbamartin.guildmanager.data.Quests
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.managers.QuestAlarmScheduler
import com.luisbarbamartin.guildmanager.managers.SaveManager
import com.luisbarbamartin.guildmanager.models.MemberStatus
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class QuestSort {
    Difficulty, Title, Gold, Experience
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestsScreen(
    drawerState: DrawerState,
    onNavigate: (AppScreen) -> Unit
) {
    val context = LocalContext.current
    val saveManager = remember { SaveManager(context) }
    val questAlarmScheduler = remember { QuestAlarmScheduler(context) }
    val scope = rememberCoroutineScope()
    
    var selectedQuestId by remember { mutableStateOf<Int?>(null) }
    var currentSort by remember { mutableStateOf(QuestSort.Difficulty) }

    // Changing quests clears the temporary party selection for the previous quest.
    var assignedMemberIds by remember(selectedQuestId) { mutableStateOf(setOf<Int>()) }
    
    var showAssignmentPanel by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var currentTimeMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTimeMillis = System.currentTimeMillis()
            delay(1000)
        }
    }

    val sortedQuests = remember(currentSort) {
        when (currentSort) {
            QuestSort.Difficulty -> Quests.all.sortedBy { it.difficultyTarget }
            QuestSort.Title -> Quests.all.sortedBy { it.title }
            QuestSort.Gold -> Quests.all.sortedByDescending { it.rewardGold }
            QuestSort.Experience -> Quests.all.sortedByDescending { it.rewardExperience }
        }
    }

    val availableMembers = GameManager.guildMembers.filter { it.status == MemberStatus.Available }
    
    val selectedQuest = Quests.all.find { it.id == selectedQuestId }

    AdaptiveScreenScaffold(onNavigate = onNavigate) {
        TopStatusBar(
            onMenuClick = {
                scope.launch { drawerState.open() }
            }
        )

        SortChipRow(
            label = "Sort by:",
            options = QuestSort.entries,
            selectedOption = currentSort,
            optionLabel = { it.name },
            onOptionSelected = { currentSort = it }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            ActiveQuestList(
                activeQuests = GameManager.activeQuests,
                guildMembers = GameManager.guildMembers,
                currentTimeMillis = currentTimeMillis
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(sortedQuests) { quest ->
                QuestCard(
                    quest = quest,
                    isSelected = selectedQuestId == quest.id,
                    onClick = {
                        selectedQuestId = if (selectedQuestId == quest.id) null else quest.id
                    }
                )

                if (selectedQuestId == quest.id) {
                    QuestDetailsPanel(
                        quest = quest,
                        assignedMembers = GameManager.guildMembers.filter { assignedMemberIds.contains(it.id) },
                        onAssignClick = { showAssignmentPanel = true },
                        onStartQuestClick = {
                            val assignedMembers = GameManager.guildMembers.filter { member ->
                                assignedMemberIds.contains(member.id)
                            }

                            GameManager.startQuest(
                                quest = quest,
                                party = assignedMembers
                            )

                            saveManager.saveGame(GameManager.toGameState())
                            questAlarmScheduler.scheduleNextQuestCompletion()

                            assignedMemberIds = emptySet()
                            selectedQuestId = null
                        }
                    )
                }
            }
        }
    }

    if (showAssignmentPanel && selectedQuest != null) {
        ModalBottomSheet(
            onDismissRequest = { showAssignmentPanel = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            MemberSelectionPanel(
                availableMembers = availableMembers,
                selectedMemberIds = assignedMemberIds,
                maxMembers = selectedQuest.maxMembers,
                onMemberClick = { memberId ->
                    assignedMemberIds = if (assignedMemberIds.contains(memberId)) {
                        assignedMemberIds - memberId
                    } else if (assignedMemberIds.size < selectedQuest.maxMembers) {
                        assignedMemberIds + memberId
                    } else {
                        assignedMemberIds
                    }
                },
                onDoneClick = { showAssignmentPanel = false }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestsScreenPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        GameManager.fromGameState(DemoData.getInitialState())
        QuestsScreen(
            drawerState = rememberDrawerState(DrawerValue.Closed),
            onNavigate = {}
        )
    }
}
