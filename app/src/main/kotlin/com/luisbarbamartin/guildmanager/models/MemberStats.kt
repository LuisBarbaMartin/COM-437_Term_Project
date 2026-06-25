package com.luisbarbamartin.guildmanager.models

data class MemberStats(
    val strength: Int = 0,
    val armor: Int = 0,
    val intellect: Int = 0,
    val agility: Int = 0,
    val wisdom: Int = 0,
    val stamina: Int = 0
) {
    operator fun plus(other: MemberStats): MemberStats {
        return MemberStats(
            strength = strength + other.strength,
            armor = armor + other.armor,
            intellect = intellect + other.intellect,
            agility = agility + other.agility,
            wisdom = wisdom + other.wisdom,
            stamina = stamina + other.stamina
        )
    }
}

data class SpecialModifiers(
    val fameGainBonus: Float = 0f, // example: 0.05 is 5
    val goldGainBonus: Float = 0f,
    val expGainBonus: Float = 0f,
    val healingBonus: Float = 0f,
    val critSuccessChance: Float = 0f,
    val itemFindBonus: Int = 0,
    val ignoresNegativeTraits: Boolean = false
) {
    operator fun plus(other: SpecialModifiers): SpecialModifiers {
        return SpecialModifiers(
            fameGainBonus = fameGainBonus + other.fameGainBonus,
            goldGainBonus = goldGainBonus + other.goldGainBonus,
            expGainBonus = expGainBonus + other.expGainBonus,
            healingBonus = healingBonus + other.healingBonus,
            critSuccessChance = critSuccessChance + other.critSuccessChance,
            itemFindBonus = itemFindBonus + other.itemFindBonus,
            ignoresNegativeTraits = ignoresNegativeTraits || other.ignoresNegativeTraits
        )
    }
}

data class MemberModifier(
    val name: String,
    val stats: MemberStats = MemberStats(),
    val specialModifiers: SpecialModifiers = SpecialModifiers()
)
