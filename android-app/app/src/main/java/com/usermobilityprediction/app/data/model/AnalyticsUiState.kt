package com.usermobilityprediction.app.data.model

data class AnalyticsUiState(

    val loading: Boolean = false,

    val error: String? = null,

    val overview: AnalyticsOverviewResponse? = null,

    val dailyDistance: List<DailyDistanceResponse> = emptyList(),

    val weeklyDistance: List<WeeklyDistanceResponse> = emptyList(),

    val prediction: PredictionAnalyticsResponse? = null,

    val safety: SafetyAnalyticsResponse? = null,

    val alerts: AlertAnalyticsResponse? = null,

    val safeZones: SafeZoneAnalyticsResponse? = null
)