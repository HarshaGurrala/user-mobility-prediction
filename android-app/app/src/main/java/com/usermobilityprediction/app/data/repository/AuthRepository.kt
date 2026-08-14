package com.usermobilityprediction.app.data.repository

import com.usermobilityprediction.app.data.model.LoginRequest
import com.usermobilityprediction.app.data.model.LoginResponse
import com.usermobilityprediction.app.data.model.RegisterRequest
import com.usermobilityprediction.app.data.model.RegisterResponse
import com.usermobilityprediction.app.data.model.UserResponse
import com.usermobilityprediction.app.data.model.UserUpdateRequest
import com.usermobilityprediction.app.data.network.RetrofitClient
import retrofit2.Response
import com.usermobilityprediction.app.data.model.ChangePasswordRequest
class AuthRepository {

    suspend fun register(
        request: RegisterRequest
    ): Response<RegisterResponse> {

        return RetrofitClient.api.register(
            request
        )
    }


    suspend fun login(
        request: LoginRequest
    ): Response<LoginResponse> {

        return RetrofitClient.api.login(
            request
        )
    }


    suspend fun getCurrentUser(): Response<UserResponse> {

        return RetrofitClient.api.getCurrentUser()
    }


    suspend fun uploadProfilePicture(
        profilePicture: okhttp3.MultipartBody.Part
    ): Response<UserResponse> {

        return RetrofitClient.api.uploadProfilePicture(
            profilePicture
        )
    }


    suspend fun updateCurrentUser(
        request: UserUpdateRequest
    ): Response<UserResponse> {

        return RetrofitClient.api.updateCurrentUser(
            request
        )
    }


    suspend fun changePassword(
        request: ChangePasswordRequest
    ): Response<Map<String, String>> {

        return RetrofitClient.api.changePassword(
            request
        )
    }
}