package com.usermobilityprediction.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.repository.AuthRepository
import com.usermobilityprediction.app.data.storage.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String = "") : AuthState()
    data class Error(val error: String) : AuthState()
}

class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        AuthRepository(application.applicationContext)

    private val tokenManager =
        TokenManager(application.applicationContext)

    private val _state =
        MutableStateFlow<AuthState>(AuthState.Idle)

    val state: StateFlow<AuthState> = _state

    fun login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _state.value = AuthState.Loading

            try {

                val response =
                    repository.login(email, password)

                if (response.isSuccessful &&
                    response.body() != null
                ) {

                    val auth = response.body()!!

                    tokenManager.saveToken(
                        auth.accessToken
                    )

                    _state.value =
                        AuthState.Success("logged_in")

                } else {

                    _state.value =
                        AuthState.Error(
                            "Invalid email or password"
                        )
                }

            } catch (e: Exception) {

                _state.value =
                    AuthState.Error(
                        e.localizedMessage
                            ?: "Network Error"
                    )
            }
        }
    }

    fun register(
        fullName: String,
        email: String,
        phone: String?,
        password: String,
        role: String
    ) {

        viewModelScope.launch {

            _state.value =
                AuthState.Loading

            try {

                val register =
                    repository.register(
                        fullName,
                        email,
                        phone,
                        password,
                        role
                    )

                if (!register.isSuccessful) {

                    _state.value =
                        AuthState.Error(
                            "Registration failed"
                        )

                    return@launch
                }

                val login =
                    repository.login(
                        email,
                        password
                    )

                if (login.isSuccessful &&
                    login.body() != null
                ) {

                    tokenManager.saveToken(
                        login.body()!!.accessToken
                    )

                    _state.value =
                        AuthState.Success(
                            "registered"
                        )

                } else {

                    _state.value =
                        AuthState.Error(
                            "Login failed"
                        )
                }

            } catch (e: Exception) {

                _state.value =
                    AuthState.Error(
                        e.localizedMessage
                            ?: "Network Error"
                    )
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }

    fun logout() {

        tokenManager.clear()

        _state.value = AuthState.Idle
    }
}