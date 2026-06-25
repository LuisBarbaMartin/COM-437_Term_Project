package com.luisbarbamartin.guildmanager.data

import com.luisbarbamartin.guildmanager.R
import com.luisbarbamartin.guildmanager.models.Quest

// Keeping quest difficulty here keeps QuestManager simpler.
object Quests {
    val all = listOf(
        Quest(
            id = 0,
            title = "Clear the Goblin Camp",
            description = "A small goblin camp is nearby. Teach them not to settle so closely to us.",
            difficulty = "Easy",
            difficultyTarget = 15,
            durationSeconds = 10,
            rewardGold = 50,
            rewardExperience = 50,
            rewardFame = 5,
            rewardItems = emptyList(),
            imageResId = R.drawable.ic_launcher_foreground,
            maxMembers = 4
        ),
        Quest(
            id = 1,
            title = "Escort the Merchant",
            description = "Protect a traveling merchant and guide them through the nearby chasm.",
            difficulty = "Medium",
            difficultyTarget = 30,
            durationSeconds = 20,
            rewardGold = 100,
            rewardExperience = 80,
            rewardFame = 10,
            rewardItems = emptyList(),
            imageResId = R.drawable.ic_launcher_foreground,
            maxMembers = 2
        ),
        Quest(
            id = 2,
            title = "Raise the Guild Banner",
            description = "Complete a public service to increase reputation.",
            difficulty = "Easy",
            difficultyTarget = 15,
            durationSeconds = 15,
            rewardGold = 75,
            rewardExperience = 60,
            rewardFame = 15,
            rewardItems = emptyList(),
            imageResId = R.drawable.ic_launcher_foreground,
            maxMembers = 1
        ),
        Quest(
            id = 3,
            title = "The Locked Cellar",
            description = "Break open a swollen cellar door, then keep steady in the dark below.",
            difficulty = "Medium",
            difficultyTarget = 23,
            durationSeconds = 10,
            rewardGold = 100,
            rewardExperience = 20,
            rewardFame = 6,
            rewardItems = listOf(
                InventoryItems.all.first { item -> item.name == "Iron Sword" }
            ),
            imageResId = R.drawable.ic_launcher_foreground,
            maxMembers = 2
        ),
        Quest(
            id = 4,
            title = "Cat in a Tree",
            description = "Climb after a stubborn cat that keeps scrambling higher into the branches.",
            difficulty = "Easy",
            difficultyTarget = 23,
            durationSeconds = 15,
            rewardGold = 20,
            rewardExperience = 30,
            rewardFame = 3,
            rewardItems = emptyList(),
            imageResId = R.drawable.ic_launcher_foreground,
            maxMembers = 1
        )
    )
}
