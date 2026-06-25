package com.luisbarbamartin.guildmanager.data

import com.luisbarbamartin.guildmanager.R
import com.luisbarbamartin.guildmanager.models.GameState
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.JobClass
import com.luisbarbamartin.guildmanager.models.MemberStatus
import com.luisbarbamartin.guildmanager.models.MemberStats
import com.luisbarbamartin.guildmanager.models.MemberTitle
import com.luisbarbamartin.guildmanager.models.Race
import com.luisbarbamartin.guildmanager.models.ZodiacSign

// This file creates a starter save file. Right now, it only includes a character named "The Guide".
// This will be updated to include a starter squad (Guild Members), recruit list, inventory,
// and active quest list.
object NewGameData {
    private val tutorialGuide = GuildMember(
        id = 0,
        name = "The Guide",
        jobClass = JobClass.Mage,
        race = Race.Human,
        level = 1,
        experience = 0,
        zodiacSign = ZodiacSign.Cancer,
        status = MemberStatus.Available,
        title = MemberTitle.None,
        imageResId = R.drawable.member_mage,
        baseStats = MemberStats(
            intellect = 3,
            wisdom = 4,
            stamina = 2
        )
    )

    fun getInitialState(guildName: String = "New Guild"): GameState {
        return GameState(
            guildName = guildName.trim(),
            guildMembers = listOf(tutorialGuide),
            recruits = emptyList(),
            inventory = emptyList(),
            activeQuests = emptyList(),
            gold = 0,
            fame = 0,
            activityLog = listOf("Your guild charter is ready.")
        )
    }
}
