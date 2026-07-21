package com.usermobilityprediction.app
import com.usermobilityprediction.app.ui.screens.LandingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.usermobilityprediction.app.data.storage.TokenManager
import com.usermobilityprediction.app.ui.HomeScreen
import com.usermobilityprediction.app.ui.SplashScreen
import com.usermobilityprediction.app.ui.auth.LoginScreen
import com.usermobilityprediction.app.ui.auth.RegisterScreen
import com.usermobilityprediction.app.ui.components.DebugOverlay
import com.usermobilityprediction.app.ui.screens.HomeDashboardScreen
import com.usermobilityprediction.app.ui.screens.LiveTrackingScreen
import com.usermobilityprediction.app.ui.screens.HistoryScreen
import com.usermobilityprediction.app.ui.screens.AIPredictionScreen
import com.usermobilityprediction.app.ui.screens.SafeZoneScreen
import com.usermobilityprediction.app.ui.screens.EmergencyScreen
import com.usermobilityprediction.app.ui.screens.NotificationsScreen
import com.usermobilityprediction.app.ui.screens.AnalyticsScreen
//import com.usermobilityprediction.app.ui.screens.ProfileScreen
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
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    Box(modifier = Modifier.fillMaxSize()) {
                        AppNavHost(navController)

                    }
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    val tokenManager = TokenManager(LocalContext.current)
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController, tokenManager) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }
        composable("home") { HomeScreen(navController, tokenManager) }

        // AppShell provides the bottom navigation and inner app routes
        composable("app") { AppShell(navController) }

        // Utility / static screens reachable outside the app shell
        composable("onboarding") { OnboardingScreen() }
        composable("forgot") { ForgotPasswordScreen() }


        composable(
            route = "landing"
        ) {
            LandingScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UserMobilityPredictionTheme {
        // Preview left intentionally blank
    }
}
