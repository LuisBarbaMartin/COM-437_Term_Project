package com.luisbarbamartin.guildmanager.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.models.InventoryItem
import com.luisbarbamartin.guildmanager.models.JobClass
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme

@Composable
fun InventoryItemCard(
    item: InventoryItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SelectableCard(
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
        padding = PaddingValues(4.dp),
        selectedBorderWidth = 3.dp,
        unselectedBorderWidth = 2.dp,
        containerColor = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.name,
                modifier = Modifier.padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun InventoryItemDetailsPanel(item: InventoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Type: ${item.itemType.name}",
                style = MaterialTheme.typography.bodyMedium
            )

            if (item.isWeapon) {
                Text(
                    text = "Slot: ${item.equipmentLabel}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = "Rarity: ${item.rarity}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Value: ${item.value} Gold",
                style = MaterialTheme.typography.bodyMedium
            )

            val visibleClassRestrictions = item.classRestrictions.filter { jobClass ->
                jobClass != JobClass.None
            }

            if (visibleClassRestrictions.isNotEmpty()) {
                Text(
                    text = "Class: ${visibleClassRestrictions.joinToString { it.name }}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            item.stats?.let { stats ->
                val statStrings = mutableListOf<String>()
                if (stats.strength != 0) statStrings.add("+${stats.strength} Str")
                if (stats.armor != 0) statStrings.add("+${stats.armor} Arm")
                if (stats.intellect != 0) statStrings.add("+${stats.intellect} Int")
                if (stats.agility != 0) statStrings.add("+${stats.agility} Agi")
                if (stats.wisdom != 0) statStrings.add("+${stats.wisdom} Wis")
                if (stats.stamina != 0) statStrings.add("+${stats.stamina} Sta")
                
                if (statStrings.isNotEmpty()) {
                    Text(
                        text = "Stats: ${statStrings.joinToString(", ")}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 100,
    heightDp = 100
)
@Composable
fun InventoryItemCardPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        InventoryItemCard(
            item = DemoData.inventoryItems[0],
            isSelected = true,
            onClick = {},
            modifier = Modifier.size(80.dp)
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 700
)
@Composable
fun InventoryItemCardDetailsPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        InventoryItemDetailsPanel(
            item = DemoData.inventoryItems[0]
        )
    }
}
