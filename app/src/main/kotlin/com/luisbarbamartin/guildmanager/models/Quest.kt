package com.luisbarbamartin.guildmanager.models

import com.luisbarbamartin.guildmanager.R

data class Quest (
    val id: Int,
    val title: String,
    val description: String,
    val difficulty: String,
    val difficultyTarget: Int,
    val durationSeconds: Int,
    val rewardGold: Int,
    val rewardExperience: Int,
    val rewardFame: Int,
    val rewardItems: List<InventoryItem>,
    val imageResId: Int = R.drawable.ic_launcher_foreground,
    val maxMembers: Int = 3 // default member limit per quest
)
