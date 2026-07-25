package com.usermobilityprediction.app.data.location

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.usermobilityprediction.app.R
import androidx.core.content.ContextCompat
class LocationForegroundService : Service() {


    private lateinit var tracker: LocationTracker

    override fun onCreate() {
        super.onCreate()

        tracker = LocationTracker(this)

        createNotificationChannel()

        val notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("SafePath")
                .setContentText("Live location tracking is active")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .build()

        startForeground(1001, notification)

        tracker.startTracking()
    }

//    override fun onDestroy() {
//
//        tracker.stopTracking()
//
//        val restartIntent = Intent(
//            applicationContext,
//            LocationForegroundService::class.java
//        )
//
////        ContextCompat.startForegroundService(
////            applicationContext,
////            restartIntent
////        )
//
//        super.onDestroy()
//
//
//    }
override fun onDestroy() {
    tracker.stopTracking()
    super.onDestroy()
}

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "Location Tracking",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager =
                getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "location_tracking_channel"
    }
}