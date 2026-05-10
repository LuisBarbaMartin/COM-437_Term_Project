package com.example.com_437termproject

import android.os.Bundle
// androidx.activity imports
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// androidx.compose imports

    //androidx.compose.foundation
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth

    // androidx.compose.material3 imports
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme

    // androidx.compose.runtime imports
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

    // androidx.compose.ui
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// com.example.com_437termproject imports
import com.example.com_437termproject.ui.theme.COM437TermProjectTheme

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

enum class AppScreen {
    Start,
    Dashboard,
    GuildBank,
    Members,
    Quests,
    Recruitment
}

// controls which screen the app displays
@Composable
fun GuildManagerApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.Start) }

    when (currentScreen) {
        AppScreen.Start -> StartScreen(
            onStartClick = {
                currentScreen = AppScreen.Dashboard
            }
        )

        AppScreen.Dashboard -> DashboardScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )

        AppScreen.GuildBank -> GuildBankScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )

        AppScreen.Members -> MembersScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )

        AppScreen.Quests -> QuestsScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )

        AppScreen.Recruitment -> RecruitmentScreen(
            onNavigate = { selectedScreen ->
                currentScreen = selectedScreen
            }
        )
    }
}

// first screen/"loading" screen.
@Composable
fun StartScreen(
    onStartClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.start_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            /* FillBounds okay for now while I use a background with blank space. Will need to be
            changed to ContentScale.Crop once I use fully colored artwork */
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White.copy(alpha = 0.6f), //affects background visibility. Lower value = more visible.
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Guild Manager",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Create your guild, recruit members, build fame, and send them on quests.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onStartClick
            ) {
                Text("Start your Journey")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = """
                COM-437 Term Project
                Luis Barba-Martin
                2026 Summer 1
                Instructor Justin Litz
            """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

// main screen after pressing start.
@Composable
fun DashboardScreen(
    onNavigate: (AppScreen) -> Unit
) {
    TopStatusBar()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineLarge
            )

            Text("Guild status will appear here.")
        }

        BottomNavBar(
            onNavigate = onNavigate
        )
    }
}

@Composable
fun GuildBankScreen(
    onNavigate: (AppScreen) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopStatusBar()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Guild Bank",
                style = MaterialTheme.typography.headlineLarge
            )

            Text("Guild items/currency will appear here.")
        }

        BottomNavBar(
            onNavigate = onNavigate
        )
    }
}

@Composable
fun MembersScreen(
    onNavigate: (AppScreen) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopStatusBar()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Guild Members",
                style = MaterialTheme.typography.headlineLarge
            )

            Text("Guild members will appear here.")
        }

        BottomNavBar(
            onNavigate = onNavigate
        )
    }
}

@Composable
fun QuestsScreen(
    onNavigate: (AppScreen) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopStatusBar()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Quests",
                style = MaterialTheme.typography.headlineLarge
            )

            Text("Quests will appear here.")
        }

        BottomNavBar(
            onNavigate = onNavigate
        )
    }
}

@Composable
fun RecruitmentScreen(
    onNavigate: (AppScreen) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopStatusBar()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Recruitment",
                style = MaterialTheme.typography.headlineLarge
            )

            Text("Recruitable NPCs will appear here.")
        }

        BottomNavBar(
            onNavigate = onNavigate
        )
    }
}



// UI stuff
@Composable
fun TopStatusBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //These fields will need to be connected to variables once developed.
        Text("Guild_Name")
        Text("Fame: 0")
        Text("Currency: 0")
    }
}

@Composable
fun BottomNavBar(
    onNavigate: (AppScreen) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                onNavigate(AppScreen.Dashboard)
            }
        ) {
            Text("Dashboard")
        }

        Button(
            onClick = {
                onNavigate(AppScreen.GuildBank)
            }
        ) {
            Text("Bank")
        }

        Button(
            onClick = {
                onNavigate(AppScreen.Members)
            }
        ) {
            Text("Members")
        }

        Button(
            onClick = {
                onNavigate(AppScreen.Quests)
            }
        ) {
            Text("Quests")
        }

        Button(
            onClick = {
                onNavigate(AppScreen.Recruitment)
            }
        ) {
            Text("Recruit")
        }
    }
}

// Previews. Used to load a specific page without emulating the entire app.
@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    COM437TermProjectTheme {
        StartScreen(
            onStartClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    COM437TermProjectTheme {
        DashboardScreen(
            onNavigate = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GuildBankScreenPreview() {
    COM437TermProjectTheme {
        GuildBankScreen(
            onNavigate = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MembersScreenPreview() {
    COM437TermProjectTheme {
        MembersScreen(
            onNavigate = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuestsScreenPreview() {
    COM437TermProjectTheme {
        QuestsScreen(
            onNavigate = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecruitmentScreenPreview() {
    COM437TermProjectTheme {
        RecruitmentScreen(
            onNavigate = {}
        )
    }
}