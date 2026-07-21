package com.usermobilityprediction.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.usermobilityprediction.app.ui.components.DebugOverlay
import com.usermobilityprediction.app.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell(rootNavController: NavController) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "dashboard"

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedRoute = currentRoute,
                onTabSelected = { route ->

                    navController.navigate(route) {

                        launchSingleTop = true

                        restoreState = true

                        popUpTo("dashboard") {
                            saveState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
//
//            NavHost(navController = navController, startDestination = "dashboard", modifier = Modifier.fillMaxSize()) {
//                composable("dashboard") { HomeDashboardScreen(navController = navController) }
//                composable("emergency") { EmergencyScreen() }
//                composable("predictions") { AIPredictionScreen() }
//                composable("safezones") { SafeZoneScreen() }
//                composable("analytics") { AnalyticsScreen() }
//
//                composable("tracking") { LiveTrackingScreen() }
//                composable("history") { HistoryScreen() }
//
//                composable("profile") { ProfileScreen() }
//                composable("notifications") { NotificationsScreen() }
//                composable("settings") { SettingsScreen() }
//                composable("about") { AboutScreen() }
//                composable("help") { HelpScreen() }
//                composable("privacy") { PrivacyScreen() }
//                composable("terms") { TermsScreen() }
//            }

            NavHost(
                navController = navController,
                startDestination = "dashboard",
                modifier = Modifier.fillMaxSize()
            ) {

                composable("dashboard") {
                    HomeDashboardScreen(navController = navController)
                }

                composable("emergency") {
                    EmergencyScreen()
                }

                composable("predictions") {
                    AIPredictionScreen()
                }

                composable("safezones") {
                    SafeZoneScreen()
                }

                composable("analytics") {
                    AnalyticsScreen()
                }

                composable("tracking") {
                    LiveTrackingScreen()
                }

                composable("history") {
                    HistoryScreen()
                }

                composable("profile") {
                    ProfileScreen()
                }

                composable("notifications") {
                    NotificationsScreen()
                }

                composable("settings") {
                    SettingsScreen()
                }

                composable("about") {
                    AboutScreen()
                }

                composable("help") {
                    HelpScreen()
                }

                composable("privacy") {
                    PrivacyScreen()
                }

                composable("terms") {
                    TermsScreen()
                }
                composable("landing") {
                    LandingScreen(navController = rootNavController)
                }

                composable("dashboard") {
                    HomeDashboardScreen(
                        navController = navController
                    )
                }
            }
        }
    }
}
