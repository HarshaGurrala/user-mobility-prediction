package com.usermobilityprediction.app.data.model

import com.google.gson.annotations.SerializedName


data class PredictionHistoryResponse(

    val id: Int,

    @SerializedName("predicted_location")
    val predictedLocation: String,

    @SerializedName("predicted_latitude")
    val predictedLatitude: Double,

    @SerializedName("predicted_longitude")
    val predictedLongitude: Double,

    @SerializedName("actual_latitude")
    val actualLatitude: Double?,

    @SerializedName("actual_longitude")
    val actualLongitude: Double?,

    val confidence: Double,

    @SerializedName("prediction_accuracy")
    val predictionAccuracy: Double?,

    val matched: Boolean,

    @SerializedName("created_at")
    val createdAt: String?

)