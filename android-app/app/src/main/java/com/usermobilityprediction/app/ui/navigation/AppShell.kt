package com.usermobilityprediction.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.usermobilityprediction.app.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell() {
    val dashboardNav = rememberNavController()
    val trackingNav = rememberNavController()
    val profileNav = rememberNavController()

    val selectedTab = remember { mutableStateOf("dashboard") }

    Scaffold(bottomBar = { BottomNavBar(selectedRoute = selectedTab.value, onTabSelected = { selectedTab.value = it }) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // Dashboard NavHost (kept composed to preserve back stack)
            NavHost(navController = dashboardNav, startDestination = "dashboard", modifier = Modifier.fillMaxSize().alpha(if (selectedTab.value == "dashboard") 1f else 0f)) {
                composable("dashboard") { HomeDashboardScreen(navController = dashboardNav) }
                composable("predictions") { AIPredictionScreen() }
                composable("safezones") { SafeZoneScreen() }
                composable("analytics") { AnalyticsScreen() }
            }

            // Tracking NavHost
            NavHost(navController = trackingNav, startDestination = "tracking", modifier = Modifier.fillMaxSize().alpha(if (selectedTab.value == "tracking") 1f else 0f)) {
                composable("tracking") { LiveTrackingScreen() }
                composable("history") { HistoryScreen() }
            }

            // Profile NavHost
            NavHost(navController = profileNav, startDestination = "profile", modifier = Modifier.fillMaxSize().alpha(if (selectedTab.value == "profile") 1f else 0f)) {
                composable("profile") { ProfileScreen() }
                composable("notifications") { NotificationsScreen() }
                composable("settings") { SettingsScreen() }
                composable("about") { AboutScreen() }
                composable("help") { HelpScreen() }
                composable("privacy") { PrivacyScreen() }
                composable("terms") { TermsScreen() }
            }
        }
    }
}
