package com.usermobilityprediction.app

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.google.android.libraries.places.api.Places

import com.usermobilityprediction.app.data.network.RetrofitClient
import com.usermobilityprediction.app.navigation.AppNavGraph
import com.usermobilityprediction.app.ui.components.RequestLocationPermission
import com.usermobilityprediction.app.ui.theme.SafePathTheme


class MainActivity : ComponentActivity() {

    private var resetIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        resetIntent = intent

        // YOUR EXISTING CODE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val powerManager =
                getSystemService(PowerManager::class.java)

            if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {

                val intent = Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                )

                intent.data =
                    Uri.parse("package:$packageName")

                startActivity(intent)
            }
        }

        RetrofitClient.initialize(
            applicationContext
        )

        if (!Places.isInitialized()) {

            Places.initialize(
                applicationContext,
                "AIzaSyCZNt80FD2w99CraiWfw2AFRzDeuD9x4Wg"
            )
        }

        setContent {

            SafePathTheme {

                RequestLocationPermission {

                    // Permission granted
                }

                val navController =
                    rememberNavController()

                AppNavGraph(
                    navController = navController
                )

                LaunchedEffect(resetIntent) {

                    val intentToHandle =
                        resetIntent

                    if (intentToHandle != null) {

                        handleResetPasswordIntent(
                            intent = intentToHandle,
                            navController = navController
                        )

                        resetIntent = null
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {

        super.onNewIntent(intent)

        setIntent(intent)

        resetIntent = intent
    }

    private fun handleResetPasswordIntent(
        intent: Intent,
        navController: NavHostController
    ) {

        val uri =
            intent.data ?: return

        if (
            uri.scheme == "safepathai" &&
            uri.host == "reset-password"
        ) {

            val token =
                uri.getQueryParameter("token")

            if (!token.isNullOrBlank()) {

                navController.navigate(
                    "reset-password?token=${Uri.encode(token)}"
                ) {
                    launchSingleTop = true
                }
            }
        }
    }
}