package com.usermobilityprediction.app.data.model

import com.google.gson.annotations.SerializedName


data class AnalyticsOverviewResponse(

    @SerializedName("total_locations")
    val totalLocations: Int,

    @SerializedName("total_distance")
    val totalDistance: Double,

    @SerializedName("safe_locations")
    val safeLocations: Int,

    @SerializedName("total_alerts")
    val totalAlerts: Int,

    @SerializedName("total_predictions")
    val totalPredictions: Int,

    @SerializedName("prediction_success_rate")
    val predictionSuccessRate: Double?,

    @SerializedName("current_safety_status")
    val currentSafetyStatus: String,

    @SerializedName("current_location")
    val currentLocation: String
)



data class DailyDistanceResponse(

    val date: String,

    val distance: Double
)



data class WeeklyDistanceResponse(

    val week: String,

    val distance: Double
)



data class PredictionAnalyticsResponse(

    @SerializedName("total_predictions")
    val totalPredictions: Int,

    @SerializedName("matched_predictions")
    val matchedPredictions: Int,

    @SerializedName("failed_predictions")
    val failedPredictions: Int,

    @SerializedName("average_confidence")
    val averageConfidence: Double,

    @SerializedName("average_accuracy")
    val averageAccuracy: Double

)



data class SafetyAnalyticsResponse(

    @SerializedName("safe_events")
    val safeEvents: Int,

    @SerializedName("warning_events")
    val warningEvents: Int,

    @SerializedName("unknown_events")
    val unknownEvents: Int,

    @SerializedName("total_events")
    val totalEvents: Int

)



data class AlertDistributionResponse(

    @SerializedName("type")
    val type: String,

    @SerializedName("count")
    val count: Int
)



data class AlertAnalyticsResponse(

    @SerializedName("total_alerts")
    val totalAlerts: Int,

    @SerializedName("read_alerts")
    val readAlerts: Int,

    @SerializedName("unread_alerts")
    val unreadAlerts: Int,

    @SerializedName("alert_distribution")
    val alertDistribution: List<AlertDistributionResponse>

)



data class SafeZoneAnalyticsResponse(

    @SerializedName("total_safe_zones")
    val totalSafeZones: Int,

    @SerializedName("zones")
    val zones: List<SafeZoneItemResponse>

)



data class SafeZoneItemResponse(

    @SerializedName("location_name")
    val locationName: String,

    @SerializedName("radius")
    val radius: Double,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double

)

data class GuardianResponse(

    val id: Int,

    val name: String,

    val email: String,

    val phone: String?,

    val safe_path_id: String,

    val status: String

)