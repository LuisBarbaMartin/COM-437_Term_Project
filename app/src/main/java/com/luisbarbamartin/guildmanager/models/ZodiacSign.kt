package com.luisbarbamartin.guildmanager.models

enum class ZodiacSign(
    val displayName: String,
    val bonus: String,
    val description: String
) {
    Aries(
        displayName = "Aries",
        bonus = "+1 Strength",
        description = "Bold and aggressive. Better at direct combat quests."
    ),

    Taurus(
        displayName = "Taurus",
        bonus = "+1 Health",
        description = "Reliable and durable. Better at surviving difficult quests."
    ),

    Gemini(
        displayName = "Gemini",
        bonus = "+1 Agility",
        description = "Flexible and quick. Better at scouting or speed-based quests."
    ),

    Cancer(
        displayName = "Cancer",
        bonus = "+1 Wisdom",
        description = "Protective and thoughtful. Better at support-focused quests."
    ),

    Leo(
        displayName = "Leo",
        bonus = "+5% Fame Gain",
        description = "Charismatic and bold. Earns extra fame from quests."
    ),

    Virgo(
        displayName = "Virgo",
        bonus = "+1 Item Find",
        description = "Careful and observant. Better chance of finding quest items."
    ),

    Libra(
        displayName = "Libra",
        bonus = "Ignores team members negative traits",
        description = "Social and balanced. Better at working with difficult team members."
    ),

    Scorpio(
        displayName = "Scorpio",
        bonus = "+2% Critical Success Chance",
        description = "Intense and focused. Better chance at high-risk quest success."
    ),

    Sagittarius(
        displayName = "Sagittarius",
        bonus = "+1 Exploration", //idk how I will implement this yet
        description = "Adventurous and curious. Better at exploration quests."
    ),

    Capricorn(
        displayName = "Capricorn",
        bonus = "+5% Gold Gain",
        description = "Disciplined and practical. Earns extra gold from quests."
    ),

    Aquarius(
        displayName = "Aquarius",
        bonus = "Bonus to magic proficiency", //idk how I will implement this yet
        description = "Creative and unusual. Better at magical or technical quests."
    ),

    Pisces(
        displayName = "Pisces",
        bonus = "+5% Healing",
        description = "Intuitive and compassionate. Better at healing or support quests."
    )
}