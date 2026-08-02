package com.usermobilityprediction.app.data.model

import com.google.gson.annotations.SerializedName


data class PredictionStatisticsResponse(

    @SerializedName("total_predictions")
    val totalPredictions: Int,

    @SerializedName("matched_predictions")
    val matchedPredictions: Int,

    @SerializedName("average_accuracy")
    val averageAccuracy: Double,

    @SerializedName("success_rate")
    val successRate: Double

)