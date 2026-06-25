package com.luisbarbamartin.guildmanager.managers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.luisbarbamartin.guildmanager.MainActivity
import com.luisbarbamartin.guildmanager.R

class GuildNotificationManager(
    private val context: Context
) {
    private val channelId = "guild_notifications"

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Guild Notifications"
            val channelDescription = "Notifications for guild events, quests, and recruitment."

            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(
                channelId,
                channelName,
                importance
            ).apply {
                description = channelDescription
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showQuestCompletedNotification(
        questTitle: String,
        isSuccess: Boolean
    ) {
        if (!hasNotificationPermission()) {
            return
        }

        val title = if (isSuccess) "Quest Complete" else "Quest Failed"
        val message = if (isSuccess) {
            "$questTitle succeeded. Your guild members have returned."
        } else {
            "$questTitle failed. Your guild members have returned."
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(appLaunchIntent())
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(
                questTitle.hashCode(),
                notification
            )
        } catch (securityException: SecurityException) {
            securityException.printStackTrace()
        }
    }

    fun showRecruitmentNotification() {
        if (!hasNotificationPermission()) {
            return
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New Recruit Available")
            .setContentText("A new adventurer is waiting to join your guild.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(appLaunchIntent())
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(
                1002,
                notification
            )
        } catch (securityException: SecurityException) {
            securityException.printStackTrace()
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun appLaunchIntent(): PendingIntent {
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(MainActivity.EXTRA_OPEN_SCREEN, MainActivity.OPEN_SCREEN_DASHBOARD)
        }

        return PendingIntent.getActivity(
            context,
            APP_LAUNCH_REQUEST_CODE,
            intent,
            pendingIntentFlags
        )
    }

    private companion object {
        const val APP_LAUNCH_REQUEST_CODE = 1001
    }
}
