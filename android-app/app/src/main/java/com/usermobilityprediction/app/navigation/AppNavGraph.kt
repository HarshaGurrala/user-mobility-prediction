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

@Composable
fun AppNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Routes.LANDING
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

        composable(Routes.CHANGE_PASSWORD) {
            ChangePasswordScreen(
                navController = navController
            )
        }
    }
}