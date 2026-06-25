package com.luisbarbamartin.guildmanager.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarbamartin.guildmanager.AppScreen
import com.luisbarbamartin.guildmanager.components.AdaptiveScreenScaffold
import com.luisbarbamartin.guildmanager.components.TopStatusBar
import com.luisbarbamartin.guildmanager.data.DemoData
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme
import kotlinx.coroutines.launch

// settings screen code
@Composable
fun SettingsScreen(
    drawerState: DrawerState,
    onNavigate: (AppScreen) -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    AdaptiveScreenScaffold(onNavigate = onNavigate) {
        // top status bar
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
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFD4AF37)
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingsSectionCard(
                title = "Guild Info",
                description = "View current guild progress and saved game information."
            ) {
                Text(
                    text = "Members: ${GameManager.guildMembers.size}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Recruits: ${GameManager.recruits.size}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Inventory Items: ${GameManager.inventoryItems.size}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsSectionCard(
                title = "Notifications",
                description = "Control reminder and quest notification behavior."
            ) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Enable Notifications",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsSectionCard(
                title = "Data",
                description = "Reset the demo data used by the app."
            ) {
                Button(
                    onClick = {
                        GameManager.fromGameState(DemoData.getInitialState())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Reset Demo Data")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsSectionCard(
                title = "About",
                description = "Course and project information."
            ) {
                Text(
                    text = "Guild Manager",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "COM-437 Term Project",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Luis Barba-Martin",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "2026 Summer 1",
                    style = MaterialTheme.typography.bodyMedium
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                
                Text(
                    text = "Special thanks to my friends who helped with testing, as well as asset, item, character, and quest creation.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// reusable settings section card
@Composable
fun SettingsSectionCard(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFF8B5A2B)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )

            content()
        }
    }
}

// preview section
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    COM437TermProjectTheme(
        darkTheme = false,
    ) {
        GameManager.fromGameState(DemoData.getInitialState())
        SettingsScreen(drawerState = rememberDrawerState(DrawerValue.Closed), onNavigate = {})
    }
}
