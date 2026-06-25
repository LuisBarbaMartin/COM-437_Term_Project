package com.luisbarbamartin.guildmanager.managers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.luisbarbamartin.guildmanager.data.Quests
import com.luisbarbamartin.guildmanager.models.ActiveQuest
import com.luisbarbamartin.guildmanager.models.GameState
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.InventoryItem
import com.luisbarbamartin.guildmanager.models.MemberStatus
import com.luisbarbamartin.guildmanager.models.Quest

// game manager runs the mutable version of GameState.kt.
object GameManager {
    private data class QuestCompletion(
        val title: String,
        val isSuccess: Boolean
    )

    var guildName by mutableStateOf("New Guild")
    var gold by mutableIntStateOf(500)
    var fame by mutableIntStateOf(0)

    val inventoryItems = mutableStateListOf<InventoryItem>()
    val guildMembers = mutableStateListOf<GuildMember>()
    val recruits = mutableStateListOf<GuildMember>()
    val activeQuests = mutableStateListOf<ActiveQuest>()
    
    val activityLog = mutableStateListOf<String>()

    fun addLog(message: String) {
        activityLog.add(message)
        // keep only the last 50 events
        if (activityLog.size > 50) {
            activityLog.removeAt(0)
        }
    }

    fun toGameState(): GameState {
        return GameState(
            guildName = guildName,
            guildMembers = guildMembers.toList(),
            recruits = recruits.toList(),
            inventory = inventoryItems.toList(),
            activeQuests = activeQuests.toList(),
            gold = gold,
            fame = fame,
            activityLog = activityLog.toList()
        )
    }

    fun fromGameState(state: GameState) {
        guildMembers.clear()
        guildMembers.addAll(state.guildMembers)
        
        recruits.clear()
        recruits.addAll(state.recruits)
        
        inventoryItems.clear()
        inventoryItems.addAll(state.inventory)

        activeQuests.clear()
        activeQuests.addAll(state.activeQuests)
        
        activityLog.clear()
        activityLog.addAll(state.activityLog)
        
        guildName = state.guildName
        gold = state.gold
        fame = state.fame
    }

    fun startQuest(quest: Quest, party: List<GuildMember>) {
        val now = System.currentTimeMillis()

        party.forEach { member ->
            member.status = MemberStatus.OnQuest
        }

        activeQuests.add(
            ActiveQuest(
                id = now,
                questId = quest.id,
                memberIds = party.map { member -> member.id },
                startedAtMillis = now,
                endsAtMillis = now + (quest.durationSeconds * 1000L)
            )
        )

        addLog("${quest.title} started.")
    }

    fun equipItem(memberId: Int, itemId: Int): Boolean {
        val memberIndex = guildMembers.indexOfFirst { member -> member.id == memberId }
        val itemIndex = inventoryItems.indexOfFirst { item -> item.id == itemId }

        if (memberIndex == -1 || itemIndex == -1) {
            return false
        }

        val member = guildMembers[memberIndex]
        val item = inventoryItems[itemIndex]

        if (!item.canBeEquippedBy(member)) {
            return false
        }

        val replacedItems = member.equipmentConflictsFor(item)

        guildMembers[memberIndex] = member.copy(
            equippedItems = member.equippedItems.filterNot { equippedItem ->
                replacedItems.any { replacedItem -> replacedItem.id == equippedItem.id }
            } + item
        )
        inventoryItems.removeAt(itemIndex)
        inventoryItems.addAll(replacedItems)
        addLog("${member.name} equipped ${item.name}.")

        return true
    }

    fun unequipItem(memberId: Int, itemId: Int): Boolean {
        val memberIndex = guildMembers.indexOfFirst { member -> member.id == memberId }

        if (memberIndex == -1) {
            return false
        }

        val member = guildMembers[memberIndex]
        val item = member.equippedItems.firstOrNull { equippedItem -> equippedItem.id == itemId }

        if (item == null) {
            return false
        }

        guildMembers[memberIndex] = member.copy(
            equippedItems = member.equippedItems.toMutableList().also { equippedItems ->
                equippedItems.remove(item)
            }
        )
        inventoryItems.add(item)
        addLog("${member.name} unequipped ${item.name}.")

        return true
    }

    fun completeFinishedQuests(
        currentTimeMillis: Long = System.currentTimeMillis(),
        onQuestCompleted: ((questTitle: String, isSuccess: Boolean) -> Unit)? = null
    ): Boolean {
        val finishedQuests = activeQuests.filter { activeQuest ->
            activeQuest.endsAtMillis <= currentTimeMillis
        }

        finishedQuests.forEach { activeQuest ->
            val completion = completeQuest(activeQuest)

            if (completion != null) {
                onQuestCompleted?.invoke(completion.title, completion.isSuccess)
            }
        }

        return finishedQuests.isNotEmpty()
    }

    private fun completeQuest(activeQuest: ActiveQuest): QuestCompletion? {
        val quest = Quests.all.find { it.id == activeQuest.questId }
        if (quest == null) {
            activeQuests.remove(activeQuest)
            return null
        }

        val party = guildMembers.filter { member ->
            activeQuest.memberIds.contains(member.id)
        }

        val result = QuestManager.processQuestResult(
            party = party,
            quest = quest
        )

        result.updatedParty.forEach { updatedMember ->
            val index = guildMembers.indexOfFirst { member ->
                member.id == updatedMember.id
            }

            if (index != -1) {
                guildMembers[index] = updatedMember
            }
        }

        gold += result.goldEarned
        fame += result.fameEarned

        if (result.isSuccess) {
            inventoryItems.addAll(quest.rewardItems)
            addLog(
                "${quest.title} succeeded! Earned ${result.goldEarned} gold and ${result.fameEarned} fame."
            )
        } else {
            addLog(
                "${quest.title} failed. Final score: ${result.finalScore}/${result.difficultyTarget}."
            )
        }

        activeQuests.remove(activeQuest)

        return QuestCompletion(
            title = quest.title,
            isSuccess = result.isSuccess
        )
    }
}
