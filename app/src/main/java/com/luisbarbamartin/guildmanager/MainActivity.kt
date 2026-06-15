package com.luisbarbamartin.guildmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.luisbarbamartin.guildmanager.ui.theme.COM437TermProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            COM437TermProjectTheme {
                GuildManagerApp()
            }
        }
    }
}