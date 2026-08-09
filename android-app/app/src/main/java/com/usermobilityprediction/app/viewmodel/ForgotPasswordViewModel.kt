package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.ForgotPasswordRequest
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading

    private val _message =
        MutableStateFlow<String?>(null)

    val message: StateFlow<String?> =
        _message

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error


    fun sendResetEmail(
        email: String
    ) {

        viewModelScope.launch {

            _loading.value = true
            _message.value = null
            _error.value = null

            try {

                val response =
                    RetrofitClient.api
                        .forgotPassword(
                            ForgotPasswordRequest(
                                email = email
                            )
                        )

                if (response.isSuccessful) {

                    _message.value =
                        response.body()?.message
                            ?: "If an account exists with this email, a reset link has been sent."

                } else {

                    _error.value =
                        response.errorBody()
                            ?.string()
                            ?: "Unable to send reset link."
                }

            } catch (e: Exception) {

                _error.value =
                    e.message
                        ?: "Something went wrong."

            } finally {
                _loading.value = false
            }


        }
    }
}