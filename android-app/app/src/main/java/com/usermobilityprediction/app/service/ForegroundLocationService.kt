package com.usermobilityprediction.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ForegroundLocationService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        // GPS tracking will be added next

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}