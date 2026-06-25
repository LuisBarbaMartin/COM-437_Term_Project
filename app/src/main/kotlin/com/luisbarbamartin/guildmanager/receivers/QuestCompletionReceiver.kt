package com.luisbarbamartin.guildmanager.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.managers.GuildNotificationManager
import com.luisbarbamartin.guildmanager.managers.QuestAlarmScheduler
import com.luisbarbamartin.guildmanager.managers.SaveManager

class QuestCompletionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val appContext = context.applicationContext
        val saveManager = SaveManager(appContext)
        val notificationManager = GuildNotificationManager(appContext)
        val scheduler = QuestAlarmScheduler(appContext)

        val savedGame = saveManager.loadGame() ?: return
        GameManager.fromGameState(savedGame)

        val completedAnyQuest = GameManager.completeFinishedQuests(
            onQuestCompleted = { questTitle, isSuccess ->
                notificationManager.showQuestCompletedNotification(
                    questTitle = questTitle,
                    isSuccess = isSuccess
                )
            }
        )

        if (completedAnyQuest) {
            saveManager.saveGame(GameManager.toGameState())
        }

        scheduler.scheduleNextQuestCompletion()
    }
}

