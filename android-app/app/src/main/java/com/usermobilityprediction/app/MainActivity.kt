package com.usermobilityprediction.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.usermobilityprediction.app.data.network.RetrofitClient
import com.usermobilityprediction.app.navigation.AppNavGraph
import com.usermobilityprediction.app.ui.theme.SafePathTheme
import com.usermobilityprediction.app.ui.components.RequestLocationPermission
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import com.google.android.libraries.places.api.Places
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val powerManager =
                getSystemService(PowerManager::class.java)

            if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {

                val intent = Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                )

                intent.data = Uri.parse("package:$packageName")

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
            }
        }
    }


}
