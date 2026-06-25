package com.luisbarbamartin.guildmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.luisbarbamartin.guildmanager.data.NewGameData
import com.luisbarbamartin.guildmanager.managers.GameManager
import com.luisbarbamartin.guildmanager.managers.GuildNotificationManager
import com.luisbarbamartin.guildmanager.managers.QuestAlarmScheduler
import com.luisbarbamartin.guildmanager.managers.SaveManager
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme

class MainActivity : ComponentActivity() {
    private lateinit var saveManager: SaveManager
    private lateinit var guildNotificationManager: GuildNotificationManager
    private lateinit var questAlarmScheduler: QuestAlarmScheduler

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        saveManager = SaveManager(this)
        guildNotificationManager = GuildNotificationManager(this)
        questAlarmScheduler = QuestAlarmScheduler(this)
        guildNotificationManager.createNotificationChannel()
        requestNotificationPermissionIfNeeded()
        
        val savedGame = saveManager.loadGame()
        if (savedGame != null) {
            GameManager.fromGameState(savedGame)
        } else {
            GameManager.fromGameState(NewGameData.getInitialState())
        }
        completeFinishedQuests()
        questAlarmScheduler.scheduleNextQuestCompletion()

        enableEdgeToEdge()

        setContent {
            COM437TermProjectTheme {
                GuildManagerApp(
                    initialScreen = initialScreen()
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        saveManager.saveGame(GameManager.toGameState())
        questAlarmScheduler.scheduleNextQuestCompletion()
    }

    private fun completeFinishedQuests() {
        if (
            GameManager.completeFinishedQuests(
                onQuestCompleted = { questTitle, isSuccess ->
                    guildNotificationManager.showQuestCompletedNotification(
                        questTitle = questTitle,
                        isSuccess = isSuccess
                    )
                }
            )
        ) {
            saveManager.saveGame(GameManager.toGameState())
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return
        }

        val permissionStatus = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        )

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun initialScreen(): AppScreen {
        return if (intent.getStringExtra(EXTRA_OPEN_SCREEN) == OPEN_SCREEN_DASHBOARD) {
            AppScreen.Dashboard
        } else {
            AppScreen.Start
        }
    }

    companion object {
        const val EXTRA_OPEN_SCREEN = "com.luisbarbamartin.guildmanager.OPEN_SCREEN"
        const val OPEN_SCREEN_DASHBOARD = "dashboard"
    }
}
