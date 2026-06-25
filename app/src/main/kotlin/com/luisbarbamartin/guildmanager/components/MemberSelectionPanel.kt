package com.luisbarbamartin.guildmanager.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme
import com.luisbarbamartin.guildmanager.ui.theme.SuccessGreen

@Composable
fun MemberSelectionPanel(
    availableMembers: List<GuildMember>,
    selectedMemberIds: Set<Int>,
    maxMembers: Int,
    onMemberClick: (Int) -> Unit,
    onDoneClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(16.dp)
    ) {
        Text(
            text = "Select Party Members (${selectedMemberIds.size}/$maxMembers)",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(80.dp),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(availableMembers) { member ->
                val isSelected = selectedMemberIds.contains(member.id)
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onMemberClick(member.id) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) SuccessGreen.copy(alpha = 0.2f) else Color.White)
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) SuccessGreen else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = member.imageResId),
                            contentDescription = null,
                            modifier = Modifier.size(56.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    
                    Text(
                        text = member.name,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Button(
            onClick = onDoneClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp)
        ) {
            Text("Finish Selection")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemberSelectionPanelPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        MemberSelectionPanel(
            availableMembers = DemoData.guildMembers,
            selectedMemberIds = setOf(0, 2),
            maxMembers = 3,
            onMemberClick = {},
            onDoneClick = {}
        )
    }
}
