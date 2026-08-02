package com.usermobilityprediction.app.data.model

import com.google.gson.annotations.SerializedName

data class SafeLocationCreateRequest(

    @SerializedName("location_name")
    val locationName: String,

    val latitude: Double,

    val longitude: Double,

    val radius: Double

)