package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.ResetPasswordRequest
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResetPasswordViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun resetPassword(
        token: String,
        newPassword: String
    ) {

        viewModelScope.launch {

            _loading.value = true
            _message.value = null
            _error.value = null

            try {

                val response =
                    RetrofitClient.api.resetPassword(
                        ResetPasswordRequest(
                            token = token,
                            new_password = newPassword
                        )
                    )

                if (response.isSuccessful) {

                    _message.value =
                        response.body()
                            ?.get("message")
                            ?: "Password reset successfully"

                } else {

                    _error.value =
                        response.errorBody()
                            ?.string()
                            ?: "Password reset failed"

                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network error"

            } finally {

                _loading.value = false

            }
        }
    }
}