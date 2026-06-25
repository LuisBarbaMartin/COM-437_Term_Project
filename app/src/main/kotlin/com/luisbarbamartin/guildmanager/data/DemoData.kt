package com.luisbarbamartin.guildmanager.data

import com.luisbarbamartin.guildmanager.models.GameState

// Demo data keeps the app populated while features are being tested.
object DemoData {

    val inventoryItems =
        InventoryItems.all

    val guildMembers =
        GuildMembers.startingMembers

    val recruits =
        Recruits.all

    val activityLog =
        ActivityLogs.startingLog

    fun getInitialState(): GameState {
        return GameState(
            guildMembers = guildMembers,
            recruits = recruits,
            inventory = inventoryItems,
            activeQuests = emptyList(),
            gold = 500,
            fame = 0,
            activityLog = activityLog
        )
    }
}
