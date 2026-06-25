package com.luisbarbamartin.guildmanager.models

data class QuestResult(
    val isSuccess: Boolean,
    val updatedParty: List<GuildMember>,
    val totalPower: Int,
    val roll: Int,
    val finalScore: Int,
    val difficultyTarget: Int,
    val goldEarned: Int,
    val fameEarned: Int
)