package com.usermobilityprediction.app.data.model

import com.google.gson.annotations.SerializedName

data class UserDashboardResponse(

//    @SerializedName("user_name")
    val userName: String,

//    @SerializedName("safety_status")
    val safetyStatus: String,

//    @SerializedName("safety_message")
    val safetyMessage: String,

//    @SerializedName("last_event")
    val lastEvent: String?,

//    @SerializedName("last_updated")
    val lastUpdated: String?,

//    @SerializedName("current_location")
    val currentLocation: String,

//    @SerializedName("tracking_status")
    val trackingStatus: String,

//    @SerializedName("safe_zone")
    val safeZone: String,

//    @SerializedName("guardian_status")
    val guardianStatus: String,

//    @SerializedName("emergency_contacts")
    val emergencyContacts: List<EmergencyContactResponse>,

//    @SerializedName("safe_locations")
    val safeLocations: List<SafeLocationResponse>,

//    @SerializedName("prediction")
    val prediction: String,

//    @SerializedName("confidence")
    val confidence: String,

//    @SerializedName("recent_alert")
    val recentAlert: String
)