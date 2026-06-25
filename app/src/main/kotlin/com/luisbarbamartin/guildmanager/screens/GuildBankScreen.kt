package com.luisbarbamartin.guildmanager.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.AppScreen
import com.luisbarbamartin.guildmanager.components.AdaptiveScreenScaffold
import com.luisbarbamartin.guildmanager.components.InventoryItemCard
import com.luisbarbamartin.guildmanager.components.InventoryItemDetailsPanel
import com.luisbarbamartin.guildmanager.components.SortChipRow
import com.luisbarbamartin.guildmanager.components.TopStatusBar
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.models.InventoryItem
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme
import com.luisbarbamartin.guildmanager.utils.toggledSelection
import kotlinx.coroutines.launch

enum class InventorySort {
    Name, Rarity, Value, Type, Class
}

@Composable
fun GuildBankScreen(
    drawerState: DrawerState,
    onNavigate: (AppScreen) -> Unit
) {
    val scope = rememberCoroutineScope()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val rowWidth = if (isLandscape) 6 else 4
    var selectedItemId by remember { mutableStateOf<Int?>(null) }
    var currentSort by remember {mutableStateOf(InventorySort.Name)}

    val sortedItems = remember(currentSort, GameManager.inventoryItems.size) {
        when (currentSort) {
            InventorySort.Name -> GameManager.inventoryItems.sortedBy { it.name }
            InventorySort.Rarity -> GameManager.inventoryItems.sortedWith(compareBy({ it.rarity }, { it.name }))
            InventorySort.Value -> GameManager.inventoryItems.sortedWith(compareByDescending<InventoryItem> { it.value }.thenBy { it.name })
            InventorySort.Type -> GameManager.inventoryItems.sortedWith(compareBy({ it.itemType.name }, { it.name }))
            InventorySort.Class -> GameManager.inventoryItems.sortedWith(
                compareBy(
                    { item -> item.classRestrictions.joinToString { it.name } },
                    { item -> item.name }
                )
            )
        }
    }

    val inventoryRows = sortedItems.chunked(rowWidth)
    // TODO: Show an empty state if the guild bank has no items.

    AdaptiveScreenScaffold(onNavigate = onNavigate) {
        TopStatusBar(
            onMenuClick = {
                scope.launch { drawerState.open() }
            }
        )

        SortChipRow(
            label = "sort by:",
            options = InventorySort.entries,
            selectedOption = currentSort,
            optionLabel = { it.name.lowercase() },
            onOptionSelected = { currentSort = it }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(inventoryRows) { rowItems ->
                val selectedItemInThisRow = rowItems.firstOrNull { item ->
                    item.id == selectedItemId
                }

                Row {
                    rowItems.forEach { item ->
                        InventoryItemCard(
                            item = item,
                            isSelected = item.id == selectedItemId,
                            onClick = {
                                selectedItemId = toggledSelection(selectedItemId, item.id)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    repeat(rowWidth - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                if (selectedItemInThisRow != null) {
                    InventoryItemDetailsPanel(
                        item = selectedItemInThisRow
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuildBankScreenPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        GameManager.fromGameState(DemoData.getInitialState())
        GuildBankScreen(
            drawerState = rememberDrawerState(DrawerValue.Closed),
            onNavigate = {}
        )
    }
}
