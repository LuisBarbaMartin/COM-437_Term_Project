package com.luisbarbamartin.guildmanager.data

import com.luisbarbamartin.guildmanager.R
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.JobClass
import com.luisbarbamartin.guildmanager.models.MemberStatus
import com.luisbarbamartin.guildmanager.models.MemberStats
import com.luisbarbamartin.guildmanager.models.MemberTitle
import com.luisbarbamartin.guildmanager.models.Race
import com.luisbarbamartin.guildmanager.models.ZodiacSign

object Recruits {
    val all = listOf(
        GuildMember(
            id = 101,
            name = "The Bender",
            jobClass = JobClass.Warrior,
            race = Race.Human,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Leo, // Aug 2
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 4,
                armor = 3,
                stamina = 4
            )
        ),
        GuildMember(
            id = 102,
            name = "Jeff Gordon",
            jobClass = JobClass.Mage,
            race = Race.Halfling,
            level = 2,
            experience = 0,
            zodiacSign = ZodiacSign.Gemini,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.jeffgordon,
            baseStats = MemberStats(
                intellect = 5,
                wisdom = 3,
                agility = 3
            )
        ),
        GuildMember(
            id = 103,
            name = "Kaelen",
            jobClass = JobClass.Warrior,
            race = Race.Human,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Aries,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 4,
                armor = 3,
                stamina = 4
            )
        ),
        GuildMember(
            id = 104,
            name = "Liora",
            jobClass = JobClass.Mage,
            race = Race.Halfling,
            level = 2,
            experience = 0,
            zodiacSign = ZodiacSign.Gemini,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                intellect = 5,
                wisdom = 3,
                agility = 3
            )
        ),
        GuildMember(
            id = 105,
            name = "Thorne",
            jobClass = JobClass.Rogue,
            race = Race.Orc,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Scorpio,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 2,
                agility = 5,
                stamina = 2
            )
        ),
        GuildMember(
            id = 106,
            name = "Adrie",
            jobClass = JobClass.Mage,
            race = Race.Elf,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Leo, // Aug 2
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.member_mage,
            baseStats = MemberStats(
                strength = 4,
                intellect = 13,
                agility = 18,
                wisdom = 5
            )
        ),
        GuildMember(
            id = 107,
            name = "Rei",
            jobClass = JobClass.Fighter,
            race = Race.Human,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Aries,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 12,
                intellect = 3,
                agility = 6,
                wisdom = 4
            )
        ),
        GuildMember(
            id = 108,
            name = "CovaDax",
            jobClass = JobClass.Paladin,
            race = Race.Human,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Virgo, // Sept 22
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 10,
                intellect = 4,
                agility = 4,
                wisdom = 11
            )
        ),
        GuildMember(
            id = 109,
            name = "Yaiga",
            jobClass = JobClass.Thief,
            race = Race.Halfling,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Cancer, // Jul 2
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 6,
                intellect = 5,
                agility = 12,
                wisdom = 4
            )
        ),
        GuildMember(
            id = 110,
            name = "Woogie Copperpot",
            jobClass = JobClass.Cleric,
            race = Race.Dwarf,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Cancer,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 6,
                intellect = 7,
                agility = 4,
                wisdom = 13
            )
        ),
        GuildMember(
            id = 111,
            name = "Bootsie Lacetight",
            jobClass = JobClass.Ranger,
            race = Race.Halfling,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Sagittarius,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 8,
                intellect = 5,
                agility = 12,
                wisdom = 7
            )
        ),
        GuildMember(
            id = 112,
            name = "Big Small",
            jobClass = JobClass.Wizard,
            race = Race.Elf,
            level = 1,
            experience = 0,
            zodiacSign = ZodiacSign.Aquarius,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.member_mage,
            baseStats = MemberStats(
                strength = 3,
                intellect = 15,
                agility = 4,
                wisdom = 8
            )
        )
    )
}
