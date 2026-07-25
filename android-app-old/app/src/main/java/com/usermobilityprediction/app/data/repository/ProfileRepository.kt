package com.usermobilityprediction.app.data.repository

import android.content.Context
import com.usermobilityprediction.app.data.models.ProfileResponse
import com.usermobilityprediction.app.data.network.RetrofitClient
import retrofit2.Response

class ProfileRepository(
    context: Context
) {

    private val api =
        RetrofitClient.profileApi(context)

    suspend fun getProfile(): Response<ProfileResponse> {
        return api.getProfile()
    }
}