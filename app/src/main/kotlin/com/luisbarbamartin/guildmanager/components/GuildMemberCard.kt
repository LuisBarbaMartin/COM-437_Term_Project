package com.luisbarbamartin.guildmanager.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.MemberStats
import com.luisbarbamartin.guildmanager.models.MemberTitle
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme

@Composable
fun GuildMemberCard(
    member: GuildMember,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
    onEquipItemClick: (() -> Unit)? = null
) {
    val cardShape = RoundedCornerShape(8.dp)

    SelectableCard(
        isSelected = isSelected,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = cardShape,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = member.zodiacSign.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 12.dp, end = 12.dp)
                    .size(150.dp)
                    .alpha(0.12f),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    MemberIdentityBox(member = member)

                    Spacer(modifier = Modifier.width(16.dp))

                    MemberSurfaceInfo(
                        member = member,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (isSelected) {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        MemberExpandedDetails(
                            member = member,
                            modifier = Modifier.weight(1f)
                        )

                        ManageEquipmentButton(onEquipItemClick = onEquipItemClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun MemberIdentityBox(member: GuildMember) {
    Column(
        modifier = Modifier.width(110.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(96.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            tonalElevation = 1.dp
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = member.imageResId),
                    contentDescription = "${member.name} portrait",
                    modifier = Modifier.size(90.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = member.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        if (member.title != MemberTitle.None) {
            Text(
                text = member.title.name,
                style = MaterialTheme.typography.labelSmall,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MemberSurfaceInfo(
    member: GuildMember,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        InfoBox(text = "Lvl ${member.level}")
        InfoBox(text = member.jobClass.name)
        InfoBox(text = member.race.name)
        InfoBox(text = "Power ${member.power}")
    }
}

@Composable
private fun MemberExpandedDetails(
    member: GuildMember,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Stats",
            style = MaterialTheme.typography.titleSmall
        )

        MemberStatBoxes(stats = member.totalStats)
        InfoBox(text = "Status ${member.status.name}")
        ZodiacBonusBox(
            zodiac = member.zodiacSign.displayName,
            bonus = member.zodiacSign.bonus
        )
        EquipmentSummaryBox(member = member)
    }
}

@Composable
private fun MemberStatBoxes(stats: MemberStats) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            StatBox(label = "STR", value = stats.strength)
            StatBox(label = "ARM", value = stats.armor)
            StatBox(label = "INT", value = stats.intellect)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            StatBox(label = "AGI", value = stats.agility)
            StatBox(label = "WIS", value = stats.wisdom)
            StatBox(label = "STA", value = stats.stamina)
        }
    }
}

@Composable
private fun ManageEquipmentButton(onEquipItemClick: (() -> Unit)?) {
    if (onEquipItemClick == null) {
        return
    }

    Button(
        onClick = onEquipItemClick,
        modifier = Modifier.widthIn(max = 180.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("Manage Equipment")
    }
}

@Composable
private fun InfoBox(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun StatBox(
    label: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Text(
            text = "$label $value",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun ZodiacBonusBox(
    zodiac: String,
    bonus: String
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text(
                text = zodiac,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = bonus,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun EquipmentSummaryBox(member: GuildMember) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Equipment",
            style = MaterialTheme.typography.titleSmall
        )

        if (member.equippedItems.isEmpty()) {
            Text(
                text = "No items equipped",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            member.equippedItems.forEach { item ->
                EquipmentBox(
                    name = item.name,
                    slot = item.equipmentLabel
                )
            }
        }
    }
}

@Composable
private fun EquipmentBox(
    name: String,
    slot: String
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = slot,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuildMemberCardPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        GuildMemberCard(
            member = DemoData.guildMembers[0]
        )
    }
}
