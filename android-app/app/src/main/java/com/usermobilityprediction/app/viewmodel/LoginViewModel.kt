package com.usermobilityprediction.app.viewmodel


import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.LoginRequest
import com.usermobilityprediction.app.data.repository.AuthRepository
import com.usermobilityprediction.app.data.storage.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.usermobilityprediction.app.data.network.RetrofitClient
import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import com.usermobilityprediction.app.data.location.LocationForegroundService



class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = AuthRepository()

    private val tokenManager =
        TokenManager(application.applicationContext)

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading

    private val _userRole =
        MutableStateFlow<String>("")

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

                val response =
                    repository.login(
                        LoginRequest(
                            email = email.trim(),
                            password = password
                        )
                    )

                if (response.isSuccessful) {

                    val loginResponse =
                        response.body()

                    if (loginResponse != null) {

                        tokenManager.saveToken(
                            loginResponse.access_token
                        )

                        val userResponse =
                            RetrofitClient.api.getCurrentUser()

                        if (userResponse.isSuccessful && userResponse.body() != null) {

                            val user = userResponse.body()!!

                            _userRole.value = user.role

                            tokenManager.saveUserId(
                                user.id
                            )

                            tokenManager.saveUserRole(
                                user.role
                            )

//                            val intent = Intent(
//                                getApplication(),
//                                LocationForegroundService::class.java
//                            )
//
//                            ContextCompat.startForegroundService(
//                                getApplication(),
//                                intent
//                            )

                            _success.value = true

                        } else {

                            _error.value =
                                "Unable to load user profile"
                        }
                    } else {

                        _error.value =
                            "Invalid server response"
                    }

                } else {

                    _error.value =
                        response.errorBody()
                            ?.string()
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