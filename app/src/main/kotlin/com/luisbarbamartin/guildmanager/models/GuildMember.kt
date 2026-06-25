package com.luisbarbamartin.guildmanager.models

data class GuildMember(
    val id: Int,
    val name: String,
    val jobClass: JobClass,
    val race: Race,
    var level: Int,
    var experience: Int = 0,
    val zodiacSign: ZodiacSign,
    var status: MemberStatus,
    var title: MemberTitle = MemberTitle.None,
    val imageResId: Int,
    val baseStats: MemberStats,
    var equippedItems: List<InventoryItem> = emptyList(),
    var modifiers: List<MemberModifier> = emptyList()
) {
    val mainHandWeapon: InventoryItem?
        get() = equippedItems.firstOrNull { item ->
            item.weaponHand == WeaponHand.MainHand
        }

    val offHandWeapon: InventoryItem?
        get() = equippedItems.firstOrNull { item ->
            item.weaponHand == WeaponHand.OffHand
        }

    val hasTwoHandedMainHandWeapon: Boolean
        get() = mainHandWeapon?.isTwoHandedMainHandWeapon == true

    fun equippedItemFor(slot: EquipmentSlot): InventoryItem? {
        return equippedItems.firstOrNull { item ->
            item.equipmentSlot == slot
        }
    }

    val totalStats: MemberStats
        get() {
            val equipmentStats = equippedItems.fold(MemberStats()) { currentStats, item ->
                currentStats + (item.stats ?: MemberStats())
            }

            val initialStats = baseStats + zodiacSign.statBonus + equipmentStats

            return modifiers.fold(initialStats) { currentStats, modifier ->
                currentStats + modifier.stats
            }
        }

    val totalSpecialModifiers: SpecialModifiers
        get() {
            val initialSpecial = zodiacSign.specialBonus

            return modifiers.fold(initialSpecial) { currentSpecial, modifier ->
                currentSpecial + modifier.specialModifiers
            }
        }

    val power: Int
        get() {
            return totalStats.strength +
                    totalStats.armor +
                    totalStats.intellect +
                    totalStats.agility +
                    totalStats.wisdom +
                    totalStats.stamina
        }

    // TODO: Use a loop here if members should be able to gain multiple levels from one quest.
    fun levelUpIfNeeded() {
        val experienceNeeded = level * 100 // simple leveling logic
        if (experience >= experienceNeeded) {
            experience -= experienceNeeded
            level++
        }
    }

    fun equipmentConflictsFor(item: InventoryItem): List<InventoryItem> {
        return when (item.weaponHand) {
            WeaponHand.MainHand -> equippedItems.filter { equippedItem ->
                equippedItem.weaponHand == WeaponHand.MainHand ||
                        (item.isTwoHandedMainHandWeapon && equippedItem.weaponHand == WeaponHand.OffHand)
            }
            WeaponHand.OffHand -> equippedItems.filter { equippedItem ->
                equippedItem.weaponHand == WeaponHand.OffHand
            }
            null -> equippedItems.filter { equippedItem ->
                equippedItem.equipmentSlot == item.equipmentSlot
            }
        }
    }
}
