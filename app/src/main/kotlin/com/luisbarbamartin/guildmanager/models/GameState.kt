package com.luisbarbamartin.guildmanager.models

// This model stores relevant data changes made while the game is live, and is also used to save
// the game state client side.
data class GameState(
    val guildName: String = "New Guild",
    val guildMembers: List<GuildMember> = emptyList(),
    val recruits: List<GuildMember> = emptyList(),
    val inventory: List<InventoryItem> = emptyList(),
    val activeQuests: List<ActiveQuest> = emptyList(),
    val gold: Int = 500,
    val fame: Int = 0,
    val activityLog: List<String> = emptyList()
)
