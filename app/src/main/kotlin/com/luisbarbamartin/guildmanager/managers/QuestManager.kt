package com.luisbarbamartin.guildmanager.managers

import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.MemberStatus
import com.luisbarbamartin.guildmanager.models.Quest
import com.luisbarbamartin.guildmanager.models.QuestResult
import kotlin.random.Random

object QuestManager {
    fun processQuestResult(
        party: List<GuildMember>,
        quest: Quest
    ): QuestResult {
        val totalPower = party.sumOf { it.power }

        // Quest checks work like a d20 roll added to the party's total power.
        val roll = Random.nextInt(1, 21)
        val finalScore = totalPower + roll
        val isSuccess = finalScore >= quest.difficultyTarget

        val totalGoldBonus = party.sumOf { it.totalSpecialModifiers.goldGainBonus.toDouble() }
        val totalFameBonus = party.sumOf { it.totalSpecialModifiers.fameGainBonus.toDouble() }

        val updatedParty = if (isSuccess) {
            party.map { member ->
                val expBonus = member.totalSpecialModifiers.expGainBonus
                val finalExp = quest.rewardExperience + (quest.rewardExperience * expBonus).toInt()

                // This mutates the existing member objects; later we may replace list items so Compose refreshes more reliably.
                member.apply {
                    experience += finalExp
                    status = MemberStatus.Available
                }.also { it.levelUpIfNeeded() }
            }
        } else {
            // even on failure, members return home
            party.map { member ->
                member.apply { status = MemberStatus.Available }
            }
        }

        return QuestResult(
            isSuccess = isSuccess,
            updatedParty = updatedParty,
            totalPower = totalPower,
            roll = roll,
            finalScore = finalScore,
            difficultyTarget = quest.difficultyTarget,
            goldEarned = if (isSuccess) {
                quest.rewardGold + (quest.rewardGold * totalGoldBonus).toInt()
            } else 0,
            fameEarned = if (isSuccess) {
                quest.rewardFame + (quest.rewardFame * totalFameBonus).toInt()
            } else 0
        )
    }
}
