package com.luisbarbamartin.guildmanager.models

val quests = listOf(
    Quest(
        id = 1,
        title = "Clear the Goblin Camp",
        description = "A nearby village needs help clearing out a small goblin camp.",
        difficulty = "Easy",
        rewardGold = 50,
        rewardExperience = 50,
        rewardFame = 5,
        rewardItems = listOf(
            InventoryItem(
                id = 1,
                name = "Rusty Dagger",
                type = "Weapon",
                description = "A worn dagger taken from a goblin scout.",
                value = 15,
                rarity = "Common"
            )
        )
    ),

    Quest(
        id = 2,
        title = "Escort the Merchant",
        description = "Protect a merchant traveling to the capital.",
        difficulty = "Medium",
        rewardGold = 100,
        rewardExperience = 80,
        rewardFame = 10,
        rewardItems = listOf(
            InventoryItem(
                id = 2,
                name = "Merchant's Token",
                type = "Special",
                description = "A token of thanks from a traveling merchant.",
                value = 40,
                rarity = "Uncommon"
            ),
            InventoryItem(
                id = 3,
                name = "Health Potion",
                type = "Consumable",
                description = "Restores health after a dangerous quest.",
                value = 10,
                rarity = "Common"
            )
        )
    )
)