package com.luisbarbamartin.guildmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.AppScreen
import com.luisbarbamartin.guildmanager.components.AdaptiveScreenScaffold
import com.luisbarbamartin.guildmanager.components.TopStatusBar
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.managers.SaveManager
import com.luisbarbamartin.guildmanager.models.EquipmentSlot
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.InventoryItem
import com.luisbarbamartin.guildmanager.models.MemberStats
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme
import kotlinx.coroutines.launch

@Composable
fun EquipmentScreen(
    memberId: Int?,
    drawerState: DrawerState,
    onNavigate: (AppScreen) -> Unit
) {
    val context = LocalContext.current
    val saveManager = remember { SaveManager(context) }
    val scope = rememberCoroutineScope()
    val member = GameManager.guildMembers.firstOrNull { guildMember ->
        guildMember.id == memberId
    }

    AdaptiveScreenScaffold(onNavigate = onNavigate) {
        TopStatusBar(
            onMenuClick = {
                scope.launch { drawerState.open() }
            }
        )

        if (member == null) {
            MissingMemberPanel(
                onBackClick = { onNavigate(AppScreen.Members) },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    EquipmentHeader(
                        member = member,
                        onBackClick = { onNavigate(AppScreen.Members) }
                    )
                }

                item {
                    StatsPanel(stats = member.totalStats)
                }

                items(EquipmentSlot.entries) { slot ->
                    EquipmentSlotPanel(
                        slot = slot,
                        member = member,
                        availableItems = GameManager.inventoryItems.filter { item ->
                            item.equipmentSlot == slot && item.canBeEquippedBy(member)
                        },
                        onEquipItem = { item ->
                            GameManager.equipItem(
                                memberId = member.id,
                                itemId = item.id
                            )
                            saveManager.saveGame(GameManager.toGameState())
                        },
                        onUnequipItem = { item ->
                            GameManager.unequipItem(
                                memberId = member.id,
                                itemId = item.id
                            )
                            saveManager.saveGame(GameManager.toGameState())
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EquipmentHeader(
    member: GuildMember,
    onBackClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Text("Back to Roster")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = member.imageResId),
                    contentDescription = "${member.name} portrait",
                    modifier = Modifier.size(88.dp),
                    contentScale = ContentScale.Fit
                )

                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = member.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${member.race.name} ${member.jobClass.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Power: ${member.power}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsPanel(stats: MemberStats) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Character Stats",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            StatRow("Strength", stats.strength)
            StatRow("Armor", stats.armor)
            StatRow("Intellect", stats.intellect)
            StatRow("Agility", stats.agility)
            StatRow("Wisdom", stats.wisdom)
            StatRow("Stamina", stats.stamina)
        }
    }
}

@Composable
private fun StatRow(
    label: String,
    value: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun EquipmentSlotPanel(
    slot: EquipmentSlot,
    member: GuildMember,
    availableItems: List<InventoryItem>,
    onEquipItem: (InventoryItem) -> Unit,
    onUnequipItem: (InventoryItem) -> Unit
) {
    val equippedItem = member.equippedItemFor(slot)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = slot.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (equippedItem != null) {
                    TextButton(onClick = { onUnequipItem(equippedItem) }) {
                        Icon(
                            imageVector = Icons.Default.RemoveCircleOutline,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text("Unequip")
                    }
                }
            }

            Text(
                text = equippedItem?.name ?: "Empty",
                style = MaterialTheme.typography.bodyLarge,
                color = if (equippedItem == null) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )

            if (equippedItem != null) {
                Text(
                    text = equippedItem.equipmentLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (availableItems.isEmpty()) {
                Text(
                    text = "No compatible items available",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                availableItems.forEach { item ->
                    EquipOptionRow(
                        item = item,
                        onEquipItem = onEquipItem
                    )
                }
            }
        }
    }
}

@Composable
private fun EquipOptionRow(
    item: InventoryItem,
    onEquipItem: (InventoryItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = item.equipmentLabel,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        OutlinedButton(onClick = { onEquipItem(item) }) {
            Icon(
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text("Equip")
        }
    }
}

@Composable
private fun MissingMemberPanel(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Guild member not found",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onBackClick) {
            Text("Back to Roster")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EquipmentScreenPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        GameManager.fromGameState(DemoData.getInitialState())
        EquipmentScreen(
            memberId = DemoData.guildMembers.first().id,
            drawerState = rememberDrawerState(DrawerValue.Closed),
            onNavigate = {}
        )
    }
}
