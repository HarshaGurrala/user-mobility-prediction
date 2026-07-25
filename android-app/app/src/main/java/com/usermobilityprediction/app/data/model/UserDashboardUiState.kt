package com.usermobilityprediction.app.data.model

data class UserDashboardUiState(

    val userName: String = "User",

    val safetyStatus: String = "SAFE",

    val safetyMessage: String = "No safety issues detected.",

    val lastEvent: String = "--",

    val lastUpdated: String = "--",

    val currentLocation: String = "Location unavailable",

    val trackingStatus: String = "Tracking Active",

    val safeZone: String = "Unknown",

    val guardianStatus: String = "No Guardian Connected",

    val emergencyContacts: List<EmergencyContactResponse> = emptyList(),

    val safeLocations: List<SafeLocationResponse> = emptyList(),

    val prediction: String = "No prediction available",

    val confidence: String = "--",

    val recentAlert: String = "No recent alerts",

    val loading: Boolean = false,

    val error: String? = null

)