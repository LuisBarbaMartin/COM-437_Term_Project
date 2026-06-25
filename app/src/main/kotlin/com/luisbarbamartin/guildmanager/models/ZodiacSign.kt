package com.luisbarbamartin.guildmanager.models

import com.luisbarbamartin.guildmanager.R

enum class ZodiacSign(
    val displayName: String,
    val bonus: String,
    val description: String,
    val imageResId: Int,
    val statBonus: MemberStats = MemberStats(),
    val specialBonus: SpecialModifiers = SpecialModifiers()
) {
    Aries(
        displayName = "Aries",
        bonus = "+1 Strength",
        statBonus = MemberStats(strength =1),
        description = "Bold and aggressive. Better at direct combat quests.",
        imageResId = R.drawable.zodiac_aries
    ),

    Taurus(
        displayName = "Taurus",
        bonus = "+1 Stamina",
        statBonus = MemberStats(stamina = 1),
        description = "Reliable and durable. Better at surviving difficult quests.",
        imageResId = R.drawable.zodiac_taurus,
    ),

    Gemini(
        displayName = "Gemini",
        bonus = "+1 Agility",
        statBonus = MemberStats(agility = 1),
        description = "Flexible and quick. Better at scouting or speed-based quests.",
        imageResId = R.drawable.zodiac_gemini,
    ),

    Cancer(
        displayName = "Cancer",
        bonus = "+1 Wisdom",
        statBonus = MemberStats(wisdom = 1),
        description = "Protective and thoughtful. Better at support-focused quests.",
        imageResId = R.drawable.zodiac_cancer,
    ),

    Leo(
        displayName = "Leo",
        bonus = "+5% Fame Gain",
        specialBonus = SpecialModifiers(fameGainBonus = 0.05f),
        description = "Confident and bold. Earns extra fame from quests.",
        imageResId = R.drawable.zodiac_leo,
    ),

    Virgo(
        displayName = "Virgo",
        bonus = "+1 Item Find",
        specialBonus = SpecialModifiers(itemFindBonus = 1),
        description = "Careful and observant. Better chance of finding quest items.",
        imageResId = R.drawable.zodiac_virgo,
    ),

    Libra(
        displayName = "Libra",
        bonus = "Ignores team members negative traits",
        specialBonus = SpecialModifiers(ignoresNegativeTraits = true),
        description = "Social and balanced. Better at working with difficult team members.",
        imageResId = R.drawable.zodiac_libra,
    ),

    Scorpio(
        displayName = "Scorpio",
        bonus = "+2% Critical Success Chance",
        specialBonus = SpecialModifiers(critSuccessChance = 0.02f),
        description = "Intense and focused. Better chance at high-risk quest success.",
        imageResId = R.drawable.zodiac_scorpio,
    ),

    Sagittarius(
        displayName = "Sagittarius",
        bonus = "+1 Exploration", //idk how I will implement this yet
        description = "Adventurous and curious. Better at exploration quests.",
        imageResId = R.drawable.zodiac_sagittarius,
    ),

    Capricorn(
        displayName = "Capricorn",
        bonus = "+5% Gold Gain",
        specialBonus = SpecialModifiers(goldGainBonus = 0.05f),
        description = "Disciplined and practical. Earns extra gold from quests.",
        imageResId = R.drawable.zodiac_capricorn,
    ),

    Aquarius(
        displayName = "Aquarius",
        bonus = "Bonus to intellect proficiency", //idk how I will implement this yet
        description = "Creative and unusual. Better at logical or technical quests.",
        imageResId = R.drawable.zodiac_aquarius,
    ),

    Pisces(
        displayName = "Pisces",
        bonus = "+5% Healing",
        specialBonus = SpecialModifiers(healingBonus = 0.05f),
        description = "Intuitive and compassionate. Better at healing or support quests.",
        imageResId = R.drawable.zodiac_pisces,
    )
}
