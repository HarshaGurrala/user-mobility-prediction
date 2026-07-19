package com.usermobilityprediction.app.data.repository

import android.content.Context
import com.usermobilityprediction.app.data.models.LoginRequest
import com.usermobilityprediction.app.data.models.LoginResponse
import com.usermobilityprediction.app.data.models.RegisterRequest
import com.usermobilityprediction.app.data.network.RetrofitClient
import retrofit2.Response

class AuthRepository(private val context: Context) {
    private val api = RetrofitClient.create(context)

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return api.login(LoginRequest(email, password))
    }

    suspend fun register(request: RegisterRequest): Response<Any> {
        return api.register(request) as Response<Any>
    }
}
