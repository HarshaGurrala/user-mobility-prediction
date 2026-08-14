package com.usermobilityprediction.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.usermobilityprediction.app.ui.screens.ChangePasswordScreen
import com.usermobilityprediction.app.ui.screens.EditProfileScreen
import com.usermobilityprediction.app.ui.screens.LandingScreen
import com.usermobilityprediction.app.ui.screens.LoginScreen
import com.usermobilityprediction.app.ui.screens.RegisterScreen
import com.usermobilityprediction.app.ui.screens.SettingsScreen
import com.usermobilityprediction.app.ui.screens.GuardianConnectScreen
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.usermobilityprediction.app.data.storage.TokenManager
import com.usermobilityprediction.app.ui.screens.ResetPasswordScreen
import com.usermobilityprediction.app.ui.screens.ForgotPasswordScreen
import com.usermobilityprediction.app.ui.screens.EmergencyContactsScreen
import com.usermobilityprediction.app.ui.screens.SafeZonesScreen
import com.usermobilityprediction.app.ui.screens.NotificationsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current

    val tokenManager = remember {
        TokenManager(context.applicationContext)
    }

    val isLoggedIn = tokenManager.isLoggedIn()

    val userId = tokenManager.getUserId()

    val startDestination =
        if (isLoggedIn) {
            Routes.APP_SHELL
        } else {
            Routes.LANDING
        }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routes.LANDING) {
            LandingScreen(
                navController = navController
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                navController = navController
            )
        }

        composable(Routes.APP_SHELL) {
            AppShell(
                rootNavController = navController
            )
        }

        composable(Routes.EDIT_PROFILE) {
            EditProfileScreen(
                navController = navController
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                navController = navController
            )
        }

        composable(Routes.SAFE_ZONES) {

            SafeZonesScreen(
                navController = navController,
                userId = userId ?: 0
            )
        }

        composable("notifications") {

            NotificationsScreen(
                navController = navController
            )
        }



        composable(Routes.CHANGE_PASSWORD) {
            ChangePasswordScreen(
                navController = navController
            )
        }

//        composable(
//            Routes.EDIT_EMERGENCY_CONTACT
//        ) { backStackEntry ->
//
//            val contactId =
//                backStackEntry.arguments
//                    ?.getString("contact_id")
//                    ?.toIntOrNull()
//                    ?: 0
//        }

        composable("forgot-password") {

            ForgotPasswordScreen(
                navController = navController
            )
        }

        composable(
            route = "reset-password?token={token}"
        ) { backStackEntry ->

            val token =
                backStackEntry.arguments
                    ?.getString("token")
                    ?: ""

            ResetPasswordScreen(
                navController = navController,
                token = token
            )
        }




        composable(
            route = "guardian_connect"
        ) {

            GuardianConnectScreen(
                navController = navController
            )

        }

        composable(
            route = Routes.EMERGENCY_CONTACTS
        ) { backStackEntry ->

            val userId = backStackEntry.arguments
                ?.getString("userId")
                ?.toIntOrNull()

            if (userId != null) {

                EmergencyContactsScreen(
                    navController = navController,
                    userId = userId
                )

            }
        }


    }
}