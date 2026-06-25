package com.luisbarbamartin.guildmanager.models

data class ActiveQuest(
    val id: Long,
    val questId: Int,
    val memberIds: List<Int>,
    val startedAtMillis: Long,
    val endsAtMillis: Long
)
