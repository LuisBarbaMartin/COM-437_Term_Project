package com.luisbarbamartin.guildmanager.data

import com.luisbarbamartin.guildmanager.R
import com.luisbarbamartin.guildmanager.models.InventoryItem
import com.luisbarbamartin.guildmanager.models.ItemType
import com.luisbarbamartin.guildmanager.models.JobClass
import com.luisbarbamartin.guildmanager.models.MainHandGrip
import com.luisbarbamartin.guildmanager.models.MemberStats
import com.luisbarbamartin.guildmanager.models.WeaponHand

object InventoryItems {
    val all = listOf(
        InventoryItem(
            id = 0,
            name = "Merchant's Token",
            itemType = ItemType.Special,
            description = "A token of thanks from a merchant.",
            classRestrictions = listOf(
                JobClass.None,
            ),
            value = 40,
            rarity = "Uncommon",
            imageResId = R.drawable.item_merchantstoken
        ),
        InventoryItem(
            id = 1,
            name = "Health Potion",
            itemType = ItemType.Consumable,
            description = "Restores health.",
            classRestrictions = listOf(
                JobClass.None,
            ),
            value = 10,
            rarity = "Common",
            imageResId = R.drawable.item_healthpotion
        ),
        InventoryItem(
            id = 2,
            name = "Guild Banner",
            itemType = ItemType.Special,
            description = "A symbol of your growing fame.",
            classRestrictions = listOf(
                JobClass.None,
            ),
            value = 100,
            rarity = "Rare",
            imageResId = R.drawable.item_guildbanner
        ),
        InventoryItem(
            id = 3,
            name = "Ghostly Main Hand",
            itemType = ItemType.Weapon,
            description = "A curiously intangible set of equipment.",
            classRestrictions = listOf(
                JobClass.DemoMaster,
            ),
            value = 500,
            rarity = "Mythical",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 100,
                intellect = 100,
                agility = 100,
                wisdom = 100,
                stamina = 100),
            weaponHand = WeaponHand.MainHand,
            mainHandGrip = MainHandGrip.OneHanded
        ),
        InventoryItem(
            id = 4,
            name = "Ghostly Off Hand",
            itemType = ItemType.Weapon,
            description = "A curiously intangible set of equipment.",
            classRestrictions = listOf(
                JobClass.DemoMaster,
            ),
            value = 500,
            rarity = "Mythical",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 100,
                intellect = 100,
                agility = 100,
                wisdom = 100,
                stamina = 100),
            weaponHand = WeaponHand.OffHand
        ),
        InventoryItem(
            id = 5,
            name = "Ghostly Armor Set",
            itemType = ItemType.Armor,
            description = "A curiously intangible set of equipment teeming with power.",
            classRestrictions = listOf(
                JobClass.DemoMaster,
            ),
            value = 500,
            rarity = "Mythical",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 600,
                intellect = 600,
                agility = 600,
                wisdom = 600,
                stamina = 600)
        ),
        InventoryItem(
            id = 6,
            name = "Promised Forever",
            itemType = ItemType.Accessory,
            description = "A promised life that will never be.",
            classRestrictions = listOf(
                JobClass.DemoMaster,
            ),
            value = 500,
            rarity = "Mythical",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 100,
                intellect = 100,
                agility = 100,
                wisdom = 100,
                stamina = 100)
        ),
        InventoryItem(
            id = 7,
            name = "Iron Sword",
            itemType = ItemType.Weapon,
            description = "A basic iron sword. Reliable, but not very strong.",
            classRestrictions = listOf(
                JobClass.Fighter,
                JobClass.Paladin,
                JobClass.Cleric,
                JobClass.Ranger,
            ),
            value = 35,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(strength = 4),
            weaponHand = WeaponHand.MainHand,
            mainHandGrip = MainHandGrip.OneHanded
        ),
        InventoryItem(
            id = 8,
            name = "Iron Shield",
            itemType = ItemType.Weapon,
            description = "Equipment fit for a recruit.",
            classRestrictions = listOf(
                JobClass.Fighter,
            ),
            value = 45,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                armor = 4,
                stamina = 5,
                wisdom = 2),
            weaponHand = WeaponHand.OffHand
        ),
        InventoryItem(
            id = 9,
            name = "Iron Plate Set",
            itemType = ItemType.Armor,
            description = "Plate plate plate.",
            classRestrictions = listOf(
                JobClass.Fighter,
            ),
            value = 40,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 2,
                agility = 1,
                stamina = 1)
        ),
        InventoryItem(
            id = 10,
            name = "Soldier's Locket",
            itemType = ItemType.Accessory,
            description = "Memories of home.",
            classRestrictions = listOf(
                JobClass.Fighter,
            ),
            value = 80,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 10,
                agility = 5,
                stamina = 5)
        ),
        InventoryItem(
            id = 11,
            name = "Fire Staff",
            itemType = ItemType.Weapon,
            description = "A staff warmed by a patient inner flame.",
            classRestrictions = listOf(
                JobClass.Mage,
            ),
            value = 90,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 1,
                intellect = 12,
                wisdom = 6),
            weaponHand = WeaponHand.MainHand,
            mainHandGrip = MainHandGrip.TwoHanded
        ),
        InventoryItem(
            id = 12,
            name = "Apprentice Wand",
            itemType = ItemType.Weapon,
            description = "A beginner focus with scorch marks near the grip.",
            classRestrictions = listOf(
                JobClass.Mage,
            ),
            value = 60,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                intellect = 6,
                wisdom = 2),
            weaponHand = WeaponHand.MainHand,
            mainHandGrip = MainHandGrip.OneHanded
        ),
        InventoryItem(
            id = 13,
            name = "Apprentice Focus",
            itemType = ItemType.Weapon,
            description = "A beginner magical focus.",
            classRestrictions = listOf(
                JobClass.Mage,
            ),
            value = 45,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                intellect = 2,
                wisdom = 1),
            weaponHand = WeaponHand.OffHand
        ),
        InventoryItem(
            id = 14,
            name = "Apprentice Set",
            itemType = ItemType.Armor,
            description = "Simple protective cloth armor set fit for a spellcaster.",
            classRestrictions = listOf(
                JobClass.Mage,
            ),
            value = 25,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats
                (intellect = 2,
                wisdom = 1)
        ),
        InventoryItem(
            id = 15,
            name = "Odd Relic",
            itemType = ItemType.Relic,
            description = "An odd relic humming with a little extra magic.",
            classRestrictions = listOf(
                JobClass.Mage,
            ),
            value = 85,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                intellect = 5,
                wisdom = 10)
        ),
        InventoryItem(
            id = 16,
            name = "Ranger's First Bow",
            itemType = ItemType.Weapon,
            description = "A first bow for a ranger learning the road.",
            classRestrictions = listOf(
                JobClass.Ranger,
            ),
            value = 70,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 5,
                agility = 10),
            weaponHand = WeaponHand.MainHand,
            mainHandGrip = MainHandGrip.TwoHanded
        ),
        InventoryItem(
            id = 17,
            name = "Cracked Dagger",
            itemType = ItemType.Weapon,
            description = "Fast in the hand, questionable in a prolonged fight.",
            classRestrictions = listOf(
                JobClass.Thief,
            ),
            value = 35,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 2,
                agility = 5),
            weaponHand = WeaponHand.MainHand,
            mainHandGrip = MainHandGrip.OneHanded
        ),
        InventoryItem(
            id = 18,
            name = "Cracked Dagger Offhand",
            itemType = ItemType.Weapon,
            description = "A chipped dagger balanced for the off hand.",
            classRestrictions = listOf(
                JobClass.Thief,
            ),
            value = 30,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 2,
                agility = 5),
            weaponHand = WeaponHand.OffHand
        ),
        InventoryItem(
            id = 19,
            name = "Leather Armor Set",
            itemType = ItemType.Armor,
            description = "Light head protection for a quick adventurer.",
            classRestrictions = listOf(
                JobClass.Rogue,
                JobClass.Ranger
            ),
            value = 25,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                agility = 2,
                stamina = 1,
                wisdom = 1)
        ),
        InventoryItem(
            id = 20,
            name = "Soul of the Archer",
            itemType = ItemType.Accessory,
            description = "More of a souvenir than an actual amulet, it honors the greatest ranger of all time.",
            classRestrictions = listOf(
                JobClass.Ranger,
            ),
            value = 65,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                agility = 2,
                stamina = 1,
                wisdom = 1)
        ),
        InventoryItem(
            id = 21,
            name = "Sunlit Amulet",
            itemType = ItemType.Accessory,
            description = "Warm to the touch, even in a cold room.",
            classRestrictions = listOf(
                JobClass.Cleric,
                JobClass.Paladin,
                JobClass.Priest,
            ),
            value = 140,
            rarity = "Epic",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(strength = 2, wisdom = 8, stamina = 2)
        ),
        InventoryItem(
            id = 22,
            name = "Strange Effigy",
            itemType = ItemType.Relic,
            description = "Powerful, probably. Wearable, apparently not.",
            classRestrictions = listOf(
                JobClass.Any,
            ),
            value = 120,
            rarity = "Mythical",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 9,
                intellect = 9,
                agility = 9,
                wisdom = 9,
                stamina = 9)
        ),
        InventoryItem(
            id = 23,
            name = "Ring of Focus",
            itemType = ItemType.Accessory,
            description = "The band feels cool whenever a bad idea gets near.",
            classRestrictions = listOf(
                JobClass.Any,
            ),
            value = 100,
            rarity = "Uncommon",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                intellect = 3,
                wisdom = 3)
        ),
        InventoryItem(
            id = 24,
            name = "Mender Regalia",
            itemType = ItemType.Armor,
            description = "A calming garment granting strength to those who stand for their allies.",
            classRestrictions = listOf(
                JobClass.Cleric,
                JobClass.Paladin,
                JobClass.Priest,
            ),
            value = 95,
            rarity = "Common",
            imageResId = R.drawable.ic_launcher_foreground,
            stats = MemberStats(
                strength = 1,
                intellect = 2,
                agility = 0,
                wisdom = 7,
                stamina = 2)
        ),
        InventoryItem(
        id = 25,
        name = "Other-Worldly Punchcard",
        itemType = ItemType.Relic,
        description = "It's a piece of paper with a hole pattern punched through it...",
        classRestrictions = listOf(
            JobClass.None
        ),
        value = 105,
        rarity = "Mythic",
        imageResId = R.drawable.ic_launcher_foreground,
        )
    )
}
