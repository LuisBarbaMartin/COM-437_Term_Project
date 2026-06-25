package com.luisbarbamartin.guildmanager.models

enum class WeaponHand {
    MainHand,
    OffHand
}

enum class MainHandGrip {
    OneHanded,
    TwoHanded
}

enum class EquipmentSlot(
    val displayName: String
) {
    MainHand("Main Hand"),
    OffHand("Off Hand"),
    Armor("Armor"),
    Accessory("Accessory")
}

enum class ItemType {
    Weapon,
    Armor,
    Accessory,
    Consumable,
    Special,
    Relic
}

data class InventoryItem (
    val id: Int,
    val name: String,
    val itemType: ItemType,
    val description: String,
    val classRestrictions: List<JobClass> = listOf(JobClass.Any),
    val value: Int,
    val rarity: String,
    val imageResId: Int,
    val stats: MemberStats? = null,
    val weaponHand: WeaponHand? = null,
    val mainHandGrip: MainHandGrip? = null
) {
    val isEquippable: Boolean
        get() = itemType in EQUIPPABLE_TYPES

    val isWeapon: Boolean
        get() = itemType == ItemType.Weapon

    val isTwoHandedMainHandWeapon: Boolean
        get() = weaponHand == WeaponHand.MainHand && mainHandGrip == MainHandGrip.TwoHanded

    val equipmentSlot: EquipmentSlot?
        get() {
            return when {
                weaponHand == WeaponHand.MainHand -> EquipmentSlot.MainHand
                weaponHand == WeaponHand.OffHand -> EquipmentSlot.OffHand
                itemType == ItemType.Armor -> EquipmentSlot.Armor
                itemType == ItemType.Accessory -> EquipmentSlot.Accessory
                else -> null
            }
        }

    val equipmentLabel: String
        get() {
            if (!isWeapon) {
                return equipmentSlot?.displayName ?: itemType.name
            }

            return when (weaponHand) {
                WeaponHand.MainHand -> when (mainHandGrip) {
                    MainHandGrip.OneHanded -> "Main Hand, 1-Handed"
                    MainHandGrip.TwoHanded -> "Main Hand, 2-Handed"
                    null -> "Main Hand"
                }
                WeaponHand.OffHand -> "Off Hand"
                null -> "Weapon"
            }
        }

    fun canBeEquippedBy(member: GuildMember): Boolean {
        if (!isEquippable || equipmentSlot == null || !matchesClassRestriction(member)) {
            return false
        }

        if (!isWeapon) {
            return true
        }

        return when (weaponHand) {
            WeaponHand.MainHand -> mainHandGrip != null
            WeaponHand.OffHand -> !member.hasTwoHandedMainHandWeapon
            null -> false
        }
    }

    private fun matchesClassRestriction(member: GuildMember): Boolean {
        return JobClass.Any in classRestrictions || member.jobClass in classRestrictions
    }

    companion object {
        val EQUIPPABLE_TYPES = setOf(ItemType.Weapon, ItemType.Armor, ItemType.Accessory)
    }
}
