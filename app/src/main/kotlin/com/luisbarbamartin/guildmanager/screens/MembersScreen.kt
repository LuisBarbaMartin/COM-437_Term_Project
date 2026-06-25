package com.luisbarbamartin.guildmanager.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.AppScreen
import com.luisbarbamartin.guildmanager.components.AdaptiveScreenScaffold
import com.luisbarbamartin.guildmanager.components.GuildMemberCard
import com.luisbarbamartin.guildmanager.components.SortChipRow
import com.luisbarbamartin.guildmanager.components.TopStatusBar
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme
import com.luisbarbamartin.guildmanager.utils.toggledSelection
import kotlinx.coroutines.launch

enum class MemberSort {
    Name, Job, Level
}

@Composable
fun MembersScreen(
    drawerState: DrawerState,
    onNavigate: (AppScreen) -> Unit,
    onManageEquipment: (Int) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var currentSort by remember { mutableStateOf(MemberSort.Name) }
    var selectedMemberId by remember { mutableStateOf<Int?>(null) }
    val guildMembers = GameManager.guildMembers.toList()

    val sortedMembers = remember(currentSort, guildMembers) {
        when (currentSort) {
            MemberSort.Name -> guildMembers.sortedBy { it.name }
            MemberSort.Job -> guildMembers.sortedBy { it.jobClass.name }
            MemberSort.Level -> guildMembers.sortedByDescending { it.level }
        }
    }
    // TODO: Show an empty state if there are no recruited guild members.
    // TODO: When member removal is added, log the removed member in GameManager.activityLog.

    AdaptiveScreenScaffold(onNavigate = onNavigate) {
        TopStatusBar(
            onMenuClick = {
                scope.launch { drawerState.open() }
            }
        )

        SortChipRow(
            label = "Sort by:",
            options = MemberSort.entries,
            selectedOption = currentSort,
            optionLabel = { it.name },
            onOptionSelected = { currentSort = it }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(sortedMembers) { member ->
                GuildMemberCard(
                    member = member,
                    isSelected = selectedMemberId == member.id,
                    onClick = {
                        selectedMemberId = toggledSelection(selectedMemberId, member.id)
                    },
                    onEquipItemClick = { onManageEquipment(member.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MembersScreenPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        GameManager.fromGameState(DemoData.getInitialState())
        MembersScreen(
            drawerState = rememberDrawerState(DrawerValue.Closed),
            onNavigate = {}
        )
    }
}
