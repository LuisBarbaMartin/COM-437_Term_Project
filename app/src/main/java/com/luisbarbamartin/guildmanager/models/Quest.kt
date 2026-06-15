package com.luisbarbamartin.guildmanager.models

data class Quest (
    val id: Int,
    val title: String,
    val description: String,
    val difficulty: String,
    val rewardGold: Int,
    val rewardExperience: Int,
    val rewardFame: Int,
    val rewardItems: List<InventoryItem>,
)