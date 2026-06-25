package com.luisbarbamartin.guildmanager.data

import com.luisbarbamartin.guildmanager.R
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.JobClass
import com.luisbarbamartin.guildmanager.models.MemberStatus
import com.luisbarbamartin.guildmanager.models.MemberStats
import com.luisbarbamartin.guildmanager.models.MemberTitle
import com.luisbarbamartin.guildmanager.models.Race
import com.luisbarbamartin.guildmanager.models.ZodiacSign

/*
template
GuildMember(
            id = INT, //int should be last id +1
            name = "NAME",
            jobClass = JobClass.CLASS,
            race = Race.RACE,
            level = INT,
            experience = INT,
            zodiacSign = ZodiacSign.SIGN,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.FILENAME,
            baseStats = MemberStats(
                strength = INT,
                intellect = INT,
                wisdom = INT,
                stamina = INT
            )
        ),


 */
object GuildMembers {
    val startingMembers = listOf(
        GuildMember(
            id = 0,
            name = "Rogelio",
            jobClass = JobClass.Mage,
            race = Race.Human,
            level = 3,
            experience = 0,
            zodiacSign = ZodiacSign.Leo,
            status = MemberStatus.Available,
            title = MemberTitle.Veteran,
            imageResId = R.drawable.member_mage,
            baseStats = MemberStats(
                strength = 2,
                intellect = 5,
                wisdom = 4,
                stamina = 3
            )
        ),
        GuildMember(
            id = 1,
            name = "Alexi",
            jobClass = JobClass.Warrior,
            race = Race.Dwarf,
            level = 2,
            experience = 0,
            zodiacSign = ZodiacSign.Taurus,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 5,
                armor = 4,
                agility = 2,
                stamina = 5
            )
        ),
        GuildMember(
            id = 2,
            name = "Nyx",
            jobClass = JobClass.Rogue,
            race = Race.Elf,
            level = 4,
            experience = 0,
            zodiacSign = ZodiacSign.Scorpio,
            status = MemberStatus.Available,
            title = MemberTitle.None,
            imageResId = R.drawable.ic_launcher_foreground,
            baseStats = MemberStats(
                strength = 3,
                agility = 6,
                wisdom = 3,
                stamina = 3
            )
        )
    )
}
