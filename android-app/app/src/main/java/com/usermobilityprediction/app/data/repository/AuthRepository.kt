package com.usermobilityprediction.app.data.repository

import android.content.Context
import com.usermobilityprediction.app.data.models.AuthResponse
import com.usermobilityprediction.app.data.models.LoginRequest
import com.usermobilityprediction.app.data.models.RegisterRequest
import com.usermobilityprediction.app.data.models.UserDto
import com.usermobilityprediction.app.data.network.RetrofitClient
import retrofit2.Response

class AuthRepository(
    context: Context
) {

    private val api =
        RetrofitClient.authApi(context)

    suspend fun login(
        email: String,
        password: String
    ): Response<AuthResponse> {

        return api.login(
            LoginRequest(
                email = email,
                password = password
            )
        )
    }

    suspend fun register(
        fullName: String,
        email: String,
        phone: String?,
        password: String,
        role: String
    ): Response<UserDto> {

        return api.register(
            RegisterRequest(
                fullName = fullName,
                email = email,
                phoneNumber = phone,
                password = password,
                role = role
            )
        )
    }

    suspend fun getProfile(): Response<UserDto> {
        return api.profile()
    }
}