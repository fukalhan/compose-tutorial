package com.example.composetutorial

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.chat.Conversation
import com.example.composetutorial.chat.SampleData
import com.example.composetutorial.counter.CounterScreen
import kotlinx.coroutines.flow.collect

val items = listOf(
    Screen.Counter,
    Screen.Chat,
)

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
    var screenTitle by remember { mutableStateOf("") }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            screenTitle = backStackEntry.destination.route.toString()
        }
    }

    Scaffold(
        topBar = { TopBar(screenTitle) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(navController, Screen.Counter.route, Modifier.padding(innerPadding)) {
            composable(Screen.Counter.route) { CounterScreen() }
            composable(Screen.Chat.route) { Conversation(messages = SampleData.conversationSample)}
        }
    }
}

@Composable
fun TopBar(title: String) {
    val screenTitle = title.replaceFirstChar { it.uppercase() }

    TopAppBar(
        title = {
            Text(
                text = screenTitle,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        },
    )
}

@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painterResource(screen.iconResId), contentDescription = null) },
                label = { Text(stringResource(screen.resId))},
                selected = currentDestination?.hierarchy?.any { it.route == screen.route} == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

