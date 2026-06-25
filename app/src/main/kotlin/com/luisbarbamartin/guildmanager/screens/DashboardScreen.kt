package com.luisbarbamartin.guildmanager.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.AppScreen
import com.luisbarbamartin.guildmanager.components.ActiveQuestList
import com.luisbarbamartin.guildmanager.components.AdaptiveScreenScaffold
import com.luisbarbamartin.guildmanager.components.TopStatusBar
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.models.MemberStatus
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme
import kotlinx.coroutines.launch
import com.luisbarbamartin.guildmanager.components.HeaderBanner
import kotlinx.coroutines.delay

@Composable
fun DashboardScreen(
    drawerState: DrawerState,
    onNavigate: (AppScreen) -> Unit
) {
    val scope = rememberCoroutineScope()
    var currentTimeMillis by androidx.compose.runtime.remember {
        mutableLongStateOf(System.currentTimeMillis())
    }

    LaunchedEffect(Unit) {
        while (true) {
            currentTimeMillis = System.currentTimeMillis()
            delay(1000)
        }
    }

    AdaptiveScreenScaffold(
        onNavigate = onNavigate,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopStatusBar(
            onMenuClick = {
                scope.launch { drawerState.open() }
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderBanner(
                text = "Guild Headquarters",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    label = "Members",
                    value = "${GameManager.guildMembers.size}",
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    label = "Available",
                    value = "${GameManager.guildMembers.count { it.status == MemberStatus.Available }}",
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    label = "Active Quests",
                    value = "${GameManager.activeQuests.size}",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ActiveQuestList(
                activeQuests = GameManager.activeQuests,
                guildMembers = GameManager.guildMembers,
                currentTimeMillis = currentTimeMillis,
                modifier = Modifier.fillMaxWidth()
            )

            if (GameManager.activeQuests.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
            }

            Text(
                text = "Recent Events",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // TODO: Show an empty state if the activity log has no entries.
                    items(GameManager.activityLog.reversed()) { log ->
                        Text(
                            text = "> $log",
                            style = MaterialTheme.typography.bodySmall,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onNavigate(AppScreen.Quests) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Check the Quest Board")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onNavigate(AppScreen.Members) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Manage Guild Roster"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    COM437TermProjectTheme(
        darkTheme = false
    ) {
        GameManager.fromGameState(DemoData.getInitialState())

        DashboardScreen(
            drawerState = rememberDrawerState(DrawerValue.Closed),
            onNavigate = {}
        )
    }
}
