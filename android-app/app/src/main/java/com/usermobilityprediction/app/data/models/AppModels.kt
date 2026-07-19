package com.usermobilityprediction.app.data.models

data class LocationPoint(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val label: String? = null
)

data class Prediction(
    val id: String,
    val placeName: String,
    val confidence: Int,
    val eta: String
)

data class SafeZone(
    val id: String,
    val name: String,
    val centerLat: Double,
    val centerLng: Double,
    val radiusMeters: Int
)

data class AppNotification(
    val id: String,
    val title: String,
    val body: String,
    val timeAgo: String,
    val read: Boolean = false
)
