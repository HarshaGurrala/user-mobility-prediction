package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.SOSRequest
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SOSViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun triggerSOS(
        latitude: Double,
        longitude: Double
    ) {

        if (_loading.value) return

        viewModelScope.launch {

            _loading.value = true
            _success.value = false
            _message.value = null

            try {

                val response = RetrofitClient.api.triggerSOS(
                    SOSRequest(
                        latitude = latitude,
                        longitude = longitude,
                        message = "Emergency! I need help."
                    )
                )

                if (response.isSuccessful) {

                    _success.value = true

                    _message.value =
                        response.body()
                            ?.get("message")
                            ?.toString()
                            ?: "SOS alert sent successfully"

                } else {

                    _message.value =
                        response.errorBody()
                            ?.string()
                            ?: "Failed to send SOS alert"
                }

            } catch (e: Exception) {

                _message.value =
                    e.localizedMessage
                        ?: "Network error"

            } finally {

                _loading.value = false
            }
        }
    }
}