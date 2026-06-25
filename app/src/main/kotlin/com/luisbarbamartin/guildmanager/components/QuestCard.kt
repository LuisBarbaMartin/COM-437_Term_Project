package com.luisbarbamartin.guildmanager.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luisbarbamartin.guildmanager.data.Quests
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.Quest
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme

@Composable
fun QuestCard(
    quest: Quest,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(
                width = if (isSelected) 3.dp else 2.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = quest.imageResId),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = quest.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Difficulty: ${quest.difficulty}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun QuestDetailsPanel(
    quest: Quest,
    assignedMembers: List<GuildMember>,
    onAssignClick: () -> Unit,
    onStartQuestClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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
                text = quest.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Rewards:",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Gold: ${quest.rewardGold}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "XP: ${quest.rewardExperience}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Fame: ${quest.rewardFame}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Time: ${quest.durationSeconds} seconds",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Items: ${formatRewardItems(quest)}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Assigned Party:",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 0 until quest.maxMembers) {
                    val member = assignedMembers.getOrNull(i)
                    
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (member == null) Color.Black.copy(alpha = 0.1f) else Color.White)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onAssignClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        if (member != null) {
                            Image(
                                painter = painterResource(id = member.imageResId),
                                contentDescription = member.name,
                                modifier = Modifier.size(56.dp),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Text(
                                text = "+",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onStartQuestClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = assignedMembers.isNotEmpty()
            ) {
                Text("Start Quest")
            }
        }
    }
}

private fun formatRewardItems(quest: Quest): String {
    return if (quest.rewardItems.isEmpty()) {
        "None"
    } else {
        quest.rewardItems.joinToString { item ->
            item.name
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestCardPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            QuestCard(
                quest = Quests.all[0],
                isSelected = true,
                onClick = {}
            )
            QuestDetailsPanel(
                quest = Quests.all[0],
                assignedMembers = emptyList(),
                onAssignClick = {},
                onStartQuestClick = {}
            )
        }
    }
}
