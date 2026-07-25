package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.UserDashboardUiState
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDashboardViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(UserDashboardUiState())

    val uiState: StateFlow<UserDashboardUiState> =
        _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {

                try {
                    loadDashboard()
                } catch (_: Exception) {
                }

                delay(3000)
            }
        }
    }

    fun refreshDashboard() {
        viewModelScope.launch {
            loadDashboard()
        }
    }

    private suspend fun loadDashboard() {

        try {

            _uiState.value = _uiState.value.copy(
                loading = true
            )

            val response = RetrofitClient.api.getUserDashboard()

            if (response.isSuccessful) {

                val data = response.body()

                if (data != null) {

                    _uiState.value = _uiState.value.copy(
                        userName = data.userName,
                        safetyStatus = data.safetyStatus,
                        safetyMessage = data.safetyMessage,
                        lastEvent = data.lastEvent ?: "--",
                        lastUpdated = data.lastUpdated ?: "--",
                        currentLocation = data.currentLocation,
                        trackingStatus = data.trackingStatus,
                        safeZone = data.safeZone,
                        guardianStatus = data.guardianStatus,
                        emergencyContacts = data.emergencyContacts,
                        safeLocations = data.safeLocations,
                        prediction = data.prediction,
                        confidence = data.confidence,
                        recentAlert = data.recentAlert,
                        loading = false,
                        error = null
                    )
                }

            } else {

                _uiState.value = _uiState.value.copy(
                    error = "Dashboard loading failed",
                    loading = false
                )
            }

        } catch (e: Exception) {

            _uiState.value = _uiState.value.copy(
                error = e.message ?: "Unknown error",
                loading = false
            )
        }
    }
}