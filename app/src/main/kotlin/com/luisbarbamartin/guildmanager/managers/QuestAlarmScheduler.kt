package com.luisbarbamartin.guildmanager.managers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.luisbarbamartin.guildmanager.receivers.QuestCompletionReceiver

class QuestAlarmScheduler(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleNextQuestCompletion() {
        val nextQuestEndMillis = GameManager.activeQuests
            .minOfOrNull { activeQuest -> activeQuest.endsAtMillis }

        if (nextQuestEndMillis == null) {
            cancelQuestCompletion()
            return
        }

        val pendingIntent = questCompletionPendingIntent(PendingIntent.FLAG_UPDATE_CURRENT)
            ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                nextQuestEndMillis,
                pendingIntent
            )
        } else {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                nextQuestEndMillis,
                pendingIntent
            )
        }
    }

    fun cancelQuestCompletion() {
        alarmManager.cancel(
            questCompletionPendingIntent(PendingIntent.FLAG_NO_CREATE)
                ?: return
        )
    }

    private fun questCompletionPendingIntent(flags: Int): PendingIntent? {
        val immutableFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }

        return PendingIntent.getBroadcast(
            context,
            QUEST_COMPLETION_REQUEST_CODE,
            Intent(context, QuestCompletionReceiver::class.java),
            flags or immutableFlag
        )
    }

    private companion object {
        const val QUEST_COMPLETION_REQUEST_CODE = 2001
    }
}
