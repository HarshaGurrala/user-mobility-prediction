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
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.usermobilityprediction.app.data.storage.TokenManager
import com.usermobilityprediction.app.ui.screens.SettingsScreen
import com.usermobilityprediction.app.ui.screens.EditProfileScreen
import com.usermobilityprediction.app.ui.screens.ChangePasswordScreen
import com.usermobilityprediction.app.ui.screens.GuardianScreen
import com.usermobilityprediction.app.ui.screens.EmergencyContactsScreen
import com.usermobilityprediction.app.ui.screens.LandingScreen
import com.usermobilityprediction.app.ui.screens.EditEmergencyContactScreen
import com.usermobilityprediction.app.ui.screens.SafeZonesScreen
import com.usermobilityprediction.app.ui.screens.AddSafeZoneScreen


@Composable
fun AppShell(
    rootNavController: NavHostController
) {

    println("APP SHELL OPENED")

    val navController = rememberNavController()
    val context = LocalContext.current

    val tokenManager = remember {
        TokenManager(context)
    }

    val userId = tokenManager.getUserId()



    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = currentRoute == Routes.LANDING,

                    onClick = {

                        navController.navigate(Routes.LANDING) {
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
            startDestination = Routes.DASHBOARD,
            modifier = Modifier
                .padding(innerPadding)
        ) {

            composable(Routes.LANDING) {

                LandingScreen(
                    navController = navController
                )

            }

            composable(Routes.HOME) {

                HomeScreen(
                    navController = navController,
                    userId = userId ?: 0
                )

            }

            composable(Routes.DASHBOARD) {

                UserDashboardScreen(
                    navController = navController,
                    userId = userId
                )

            }

            composable(Routes.ANALYTICS) {

                AnalyticsScreen(
                    navController = navController,
                    userId = userId
                )

            }

            composable(Routes.PROFILE) {

                ProfileScreen(
                    navController = navController,
                    rootNavController = rootNavController
                )
            }
            composable(Routes.SETTINGS) {
                SettingsScreen(
                    navController = navController
                )
            }

            composable(Routes.EDIT_PROFILE) {
                EditProfileScreen(
                    navController = navController
                )
            }

            composable(Routes.CHANGE_PASSWORD) {
                ChangePasswordScreen(
                    navController = navController
                )
            }

            composable(Routes.GUARDIANS) {

                GuardianScreen(
                    navController = navController,
                    userId = userId ?: 0
                )
            }

            composable(Routes.SAFE_ZONES) {

                SafeZonesScreen(
                    navController = navController,
                    userId = userId ?: 0
                )

            }








            composable(Routes.EMERGENCY_CONTACTS) {
                EmergencyContactsScreen(
                    navController = navController,
                    userId = userId ?: 0
                )
            }



            composable(
                route = Routes.EDIT_EMERGENCY_CONTACT
            ) { backStackEntry ->


                val contactId =
                    backStackEntry.arguments
                        ?.getString("contact_id")
                        ?.toIntOrNull()
                        ?: 0


                val name =
                    backStackEntry.arguments
                        ?.getString("name")
                        ?: ""


                val relationship =
                    backStackEntry.arguments
                        ?.getString("relationship")
                        ?: ""


                val phone =
                    backStackEntry.arguments
                        ?.getString("phone")
                        ?: ""


                val email =
                    backStackEntry.arguments
                        ?.getString("email")
                        ?: ""


                EditEmergencyContactScreen(

                    navController = navController,

                    contactId = contactId,

                    userId = userId ?: 0,

                    name = name,

                    relationship = relationship,

                    phone = phone,

                    email = email

                )

            }

        }
    }
}