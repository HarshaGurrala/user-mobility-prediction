package com.usermobilityprediction.app.data.models

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone_number")
    val phoneNumber: String?,

    @SerializedName("role")
    val role: String,

    @SerializedName("safe_path_id")
    val safePathId: String
)