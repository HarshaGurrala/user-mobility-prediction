package com.usermobilityprediction.app.data.model

data class LocationRequest(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float? = null
)