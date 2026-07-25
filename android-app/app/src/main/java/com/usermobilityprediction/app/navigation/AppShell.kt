package com.usermobilityprediction.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SpaceDashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.usermobilityprediction.app.ui.screens.AnalyticsScreen
import com.usermobilityprediction.app.ui.screens.HomeScreen
import com.usermobilityprediction.app.ui.screens.ProfileScreen
import com.usermobilityprediction.app.ui.screens.UserDashboardScreen

@Composable
fun AppShell(
    rootNavController: NavHostController
) {

    println("APP SHELL OPENED")

    val navController = rememberNavController()



    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = {

                        rootNavController.navigate(Routes.LANDING) {

                            popUpTo(Routes.APP_SHELL) {
                                inclusive = true
                            }

                        }

                    },
                    icon = {
                        Icon(Icons.Default.Home, null)
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.HOME,

                    onClick = {

                        navController.navigate(Routes.HOME) {

                            launchSingleTop = true

                        }

                    },

                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Dashboard")
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.ANALYTICS,
                    onClick = {
                        navController.navigate(Routes.ANALYTICS, navOptions {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        })
                    },
                    icon = {
                        Icon(Icons.Default.ShowChart, null)
                    },
                    label = {
                        Text("Analytics")
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.PROFILE,
                    onClick = {
                        navController.navigate(Routes.PROFILE, navOptions {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        })
                    },
                    icon = {
                        Icon(Icons.Default.Person, null)
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier
                .padding(innerPadding)
        ) {

            composable(Routes.HOME) {
                HomeScreen(navController)
            }

            composable(Routes.DASHBOARD) {
                UserDashboardScreen(
                    navController = navController
                )
            }

            composable(Routes.ANALYTICS) {
                AnalyticsScreen(navController)
            }

            composable(Routes.PROFILE) {
                ProfileScreen(navController)
            }
        }
    }
}