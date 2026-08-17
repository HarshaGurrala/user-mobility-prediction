package com.usermobilityprediction.app.data.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.usermobilityprediction.app.data.storage.TokenManager

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {

            val tokenManager = TokenManager(context)

           if (
    tokenManager.isLoggedIn() &&
    tokenManager.getUserId() != -1 &&
    tokenManager.getUserRole().equals(
        "USER",
        ignoreCase = true
    )
) {

                val serviceIntent = Intent(
                    context,
                    LocationForegroundService::class.java
                )

                ContextCompat.startForegroundService(
                    context,
                    serviceIntent
                )
            }
        }
    }
}