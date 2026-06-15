package com.luisbarbamartin.guildmanager.models

data class GuildMember(
    val name: String,
    val jobClass: String,
    val level: Int,
    val zodiacSign: ZodiacSign,
    val bonus: String,
    val stats: Map<String, Int>,
)