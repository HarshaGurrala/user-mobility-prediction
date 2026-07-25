package com.usermobilityprediction.app.data.model


data class SafeLocationResponse(

    val id: Int,

    val locationName: String,

    val latitude: Double,

    val longitude: Double,

    val radius: Double

)