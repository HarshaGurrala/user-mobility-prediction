package com.usermobilityprediction.app.data.mock

import com.usermobilityprediction.app.data.models.AppNotification
import com.usermobilityprediction.app.data.models.LocationPoint
import com.usermobilityprediction.app.data.models.Prediction
import com.usermobilityprediction.app.data.models.SafeZone
import kotlinx.coroutines.delay
import java.util.*

class MockRepository {
    suspend fun getRecentLocations(): List<LocationPoint> {
        delay(300)
        val now = System.currentTimeMillis()
        return listOf(
            LocationPoint(UUID.randomUUID().toString(), 37.7749, -122.4194, now - 60_000, "Home"),
            LocationPoint(UUID.randomUUID().toString(), 37.7840, -122.4090, now - 300_000, "Work"),
            LocationPoint(UUID.randomUUID().toString(), 37.7890, -122.4010, now - 900_000, "Market")
        )
    }

    suspend fun getPredictions(): List<Prediction> {
        delay(250)
        return listOf(
            Prediction(UUID.randomUUID().toString(), "Downtown Station", 92, "12 min"),
            Prediction(UUID.randomUUID().toString(), "Central Park", 78, "23 min")
        )
    }

    suspend fun getSafeZones(): List<SafeZone> {
        delay(200)
        return listOf(
            SafeZone(UUID.randomUUID().toString(), "Home Zone", 37.7749, -122.4194, 200),
            SafeZone(UUID.randomUUID().toString(), "Office Zone", 37.7840, -122.4090, 150)
        )
    }

    suspend fun getNotifications(): List<AppNotification> {
        delay(150)
        return listOf(
            AppNotification(UUID.randomUUID().toString(), "Safe arrival", "Your guardian was notified.", "2h ago", false),
            AppNotification(UUID.randomUUID().toString(), "Prediction ready", "AI predicted next destination.", "1d ago", true)
        )
    }
}
