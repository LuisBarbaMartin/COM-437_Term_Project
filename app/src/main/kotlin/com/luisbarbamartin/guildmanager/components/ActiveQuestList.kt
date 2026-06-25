package com.luisbarbamartin.guildmanager.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.data.Quests
import com.luisbarbamartin.guildmanager.models.ActiveQuest
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.Quest

@Composable
fun ActiveQuestList(
    activeQuests: List<ActiveQuest>,
    guildMembers: List<GuildMember>,
    currentTimeMillis: Long,
    modifier: Modifier = Modifier
) {
    if (activeQuests.isEmpty()) {
        return
    }

    Column(modifier = modifier) {
        Text(
            text = "Active Quests",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        activeQuests.forEach { activeQuest ->
            val quest = Quests.all.find { it.id == activeQuest.questId }
            if (quest != null) {
                ActiveQuestCard(
                    quest = quest,
                    activeQuest = activeQuest,
                    guildMembers = guildMembers,
                    currentTimeMillis = currentTimeMillis
                )
            }
        }
    }
}

@Composable
private fun ActiveQuestCard(
    quest: Quest,
    activeQuest: ActiveQuest,
    guildMembers: List<GuildMember>,
    currentTimeMillis: Long
) {
    val totalDuration = (activeQuest.endsAtMillis - activeQuest.startedAtMillis).coerceAtLeast(1L)
    val elapsed = (currentTimeMillis - activeQuest.startedAtMillis).coerceIn(0L, totalDuration)
    val remainingSeconds = ((activeQuest.endsAtMillis - currentTimeMillis).coerceAtLeast(0L) / 1000L)
    val progress = elapsed.toFloat() / totalDuration.toFloat()
    val memberNames = guildMembers
        .filter { member -> activeQuest.memberIds.contains(member.id) }
        .joinToString { member -> member.name }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = quest.title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = memberNames,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "$remainingSeconds seconds remaining",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

