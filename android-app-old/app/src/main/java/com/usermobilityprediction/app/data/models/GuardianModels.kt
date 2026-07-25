package com.usermobilityprediction.app.data.models

import com.google.gson.annotations.SerializedName

data class GuardianConnectRequest(

    @SerializedName("safe_path_id")
    val safePathId: String
)

data class GuardianPendingRequest(

    @SerializedName("request_id")
    val requestId: Int,

    @SerializedName("guardian_id")
    val guardianId: Int,

    @SerializedName("guardian_name")
    val guardianName: String,

    @SerializedName("guardian_email")
    val guardianEmail: String,

    @SerializedName("status")
    val status: String
)

data class ConnectedUser(

    @SerializedName("id")
    val id: Int,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone_number")
    val phoneNumber: String?,

    @SerializedName("safe_path_id")
    val safePathId: String
)

data class MessageResponse(

    @SerializedName("message")
    val message: String
)