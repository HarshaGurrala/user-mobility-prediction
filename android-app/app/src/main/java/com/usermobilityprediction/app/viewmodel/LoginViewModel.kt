package com.usermobilityprediction.app.viewmodel

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.usermobilityprediction.app.data.location.LocationTrackingService
import com.usermobilityprediction.app.data.model.LoginRequest
import com.usermobilityprediction.app.data.repository.AuthRepository
import com.usermobilityprediction.app.data.storage.DeviceManager
import com.usermobilityprediction.app.data.storage.TokenManager
import com.usermobilityprediction.app.data.network.RetrofitClient

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        AuthRepository()

    private val tokenManager =
        TokenManager(
            application.applicationContext
        )

    private val deviceManager =
        DeviceManager(
            application.applicationContext
        )

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading


    private val _userRole =
        MutableStateFlow("")

    val userRole: StateFlow<String> =
        _userRole


    private val _success =
        MutableStateFlow(false)

    val success: StateFlow<Boolean> =
        _success


    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error


    fun login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _loading.value = true

            _success.value = false

            _error.value = null


            try {

                // ==========================================
                // GET THIS PHONE'S UNIQUE DEVICE ID
                // ==========================================

                val deviceId =
                    deviceManager.getDeviceId()


                // ==========================================
                // LOGIN
                // ==========================================

                val response =
                    repository.login(

                        LoginRequest(

                            email =
                                email.trim(),

                            password =
                                password,

                            device_id =
                                deviceId
                        )
                    )


                // ==========================================
                // SUCCESS
                // ==========================================

                if (response.isSuccessful) {

                    val loginResponse =
                        response.body()


                    if (loginResponse != null) {

                        // Save JWT
                        tokenManager.saveToken(
                            loginResponse.access_token
                        )


                        // ==================================
                        // GET CURRENT USER
                        // ==================================

                        val userResponse =
                            RetrofitClient.api
                                .getCurrentUser()


                        if (
                            userResponse.isSuccessful &&
                            userResponse.body() != null
                        ) {

                            val user =
                                userResponse.body()!!


                            _userRole.value =
                                user.role


                            tokenManager.saveUserId(
                                user.id
                            )


                            tokenManager.saveUserRole(
                                user.role
                            )


                            // ==================================
                            // START LOCATION TRACKING
                            // ONLY FOR USER
                            // ==================================

                            if (
                                user.role.equals(
                                    "USER",
                                    ignoreCase = true
                                )
                            ) {

                                val intent =
                                    Intent(
                                        getApplication(),
                                        LocationTrackingService::class.java
                                    )

                                ContextCompat
                                    .startForegroundService(
                                        getApplication(),
                                        intent
                                    )
                            }


                            _success.value = true

                        } else {

                            // Remove token if profile loading fails
                            tokenManager.clearAll()

                            _error.value =
                                "Unable to load user profile"
                        }

                    } else {

                        _error.value =
                            "Invalid server response"
                    }

                } else {

                    // ==========================================
                    // BACKEND ERROR
                    // ==========================================

                    val errorBody =
                        response.errorBody()
                            ?.string()

                    _error.value =
                        errorBody
                            ?: "Login failed"
                }


            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Unable to connect to server"

            } finally {

                _loading.value = false
            }
        }
    }
}