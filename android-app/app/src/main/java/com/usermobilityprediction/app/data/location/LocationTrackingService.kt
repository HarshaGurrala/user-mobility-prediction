package com.usermobilityprediction.app.data.location

    import android.app.Notification
            import android.app.NotificationChannel
            import android.app.NotificationManager
            import android.app.Service
            import android.content.Intent
            import android.os.IBinder

    class LocationTrackingService : Service() {

        private lateinit var locationTracker: LocationTracker

        override fun onCreate() {
            super.onCreate()

            locationTracker =
                LocationTracker(applicationContext)

            createNotificationChannel()

            startForeground(
                1001,
                createNotification()
            )

            locationTracker.startTracking()
        }

        override fun onStartCommand(
            intent: Intent?,
            flags: Int,
            startId: Int
        ): Int {

            return START_STICKY
        }

        override fun onDestroy() {

            locationTracker.stopTracking()

            super.onDestroy()
        }

        override fun onBind(
            intent: Intent?
        ): IBinder? = null

        private fun createNotificationChannel() {

            val channel =
                NotificationChannel(
                    "location_tracking",
                    "SafePath Location Tracking",
                    NotificationManager.IMPORTANCE_LOW
                )

            val manager =
                getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(channel)
        }

        private fun createNotification(): Notification {

            return Notification.Builder(
                this,
                "location_tracking"
            )
                .setContentTitle(
                    "SafePath AI"
                )
                .setContentText(
                    "Location sharing is active"
                )
                .setSmallIcon(
                    android.R.drawable.ic_menu_mylocation
                )
                .build()
        }
    }