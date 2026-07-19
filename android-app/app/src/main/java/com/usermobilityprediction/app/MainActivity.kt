package com.usermobilityprediction.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.platform.LocalContext
import com.usermobilityprediction.app.data.storage.TokenManager
import com.usermobilityprediction.app.ui.HomeScreen
import com.usermobilityprediction.app.ui.SplashScreen
import com.usermobilityprediction.app.ui.auth.LoginScreen
import com.usermobilityprediction.app.ui.auth.RegisterScreen
import com.usermobilityprediction.app.ui.screens.HomeDashboardScreen
import com.usermobilityprediction.app.ui.screens.LiveTrackingScreen
import com.usermobilityprediction.app.ui.screens.HistoryScreen
import com.usermobilityprediction.app.ui.screens.AIPredictionScreen
import com.usermobilityprediction.app.ui.screens.SafeZoneScreen
import com.usermobilityprediction.app.ui.screens.EmergencyScreen
import com.usermobilityprediction.app.ui.screens.NotificationsScreen
import com.usermobilityprediction.app.ui.screens.AnalyticsScreen
import com.usermobilityprediction.app.ui.screens.ProfileScreen
import com.usermobilityprediction.app.ui.screens.SettingsScreen
import com.usermobilityprediction.app.ui.screens.OnboardingScreen
import com.usermobilityprediction.app.ui.screens.ForgotPasswordScreen
import com.usermobilityprediction.app.ui.screens.AboutScreen
import com.usermobilityprediction.app.ui.screens.HelpScreen
import com.usermobilityprediction.app.ui.screens.PrivacyScreen
import com.usermobilityprediction.app.ui.screens.TermsScreen
import com.usermobilityprediction.app.ui.navigation.AppShell
import com.usermobilityprediction.app.ui.theme.UserMobilityPredictionTheme
import com.usermobilityprediction.app.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserMobilityPredictionTheme {
                Surface {
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val tokenManager = TokenManager(LocalContext.current)
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController, tokenManager) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }
        composable("home") { HomeScreen(navController, tokenManager) }

        // AppShell provides the bottom navigation and inner app routes
        composable("app") { AppShell() }

        // Utility / static screens reachable outside the app shell
        composable("onboarding") { OnboardingScreen() }
        composable("forgot") { ForgotPasswordScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UserMobilityPredictionTheme {
        // Preview left intentionally blank
    }
}
