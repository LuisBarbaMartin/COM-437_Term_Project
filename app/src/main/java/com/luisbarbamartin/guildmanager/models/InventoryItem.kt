package com.luisbarbamartin.guildmanager.models

data class InventoryItem (
    val id: Int,
    val name: String,
    val type: String,
    val description: String,
    val value: Int,
    val rarity: String,
)