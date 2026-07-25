package com.usermobilityprediction.app.data.model

data class UserDashboardResponse(

    val userName: String,

    val safetyStatus: String,

    val safetyMessage: String,

    val lastEvent: String?,

    val lastUpdated: String?,

    val currentLocation: String,

    val trackingStatus: String,

    val safeZone: String,

    val guardianStatus: String,

    val emergencyContacts: List<EmergencyContactResponse>,

    val safeLocations: List<SafeLocationResponse>,

    val prediction: String,

    val confidence: String,

    val recentAlert: String

)