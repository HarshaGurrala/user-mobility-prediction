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
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class UserDashboardViewModel : ViewModel() {


    private val _uiState =
        MutableStateFlow(
            UserDashboardUiState()
        )


    val uiState: StateFlow<UserDashboardUiState> =
        _uiState.asStateFlow()


    private val refreshMutex =
        Mutex()


    /*
     * Start dashboard auto refresh
     */
    fun startDashboard(
        userId: Int
    ) {

        viewModelScope.launch {

            while (true) {

                loadDashboard(
                    userId
                )

                delay(3000)
            }
        }
    }


    /*
     * Manual refresh
     */
    fun refreshDashboard(
        userId: Int
    ) {

        viewModelScope.launch {

            loadDashboard(
                userId
            )

        }

    }


    private suspend fun loadDashboard(
        userId: Int
    ) {

        refreshMutex.withLock {

            try {

                _uiState.value =
                    _uiState.value.copy(
                        loading = true
                    )

                /*
                 * Dashboard API
                 */
                val response =
                    RetrofitClient.api
                        .getUserDashboard()

                // Print the dashboard response
                android.util.Log.d(
                    "DASHBOARD_RESPONSE",
                    "Success = ${response.isSuccessful}"
                )

                android.util.Log.d(
                    "DASHBOARD_RESPONSE",
                    "Body = ${response.body()}"
                )

                /*
                 * Latest Prediction API
                 */
                val predictionResponse =
                    RetrofitClient.predictionApi
                        .getLatestPrediction(userId)

                val prediction =
                    if (predictionResponse.isSuccessful)
                        predictionResponse.body()
                    else
                        null

                if (response.isSuccessful) {

                    val data = response.body()

                    android.util.Log.d(
                        "DASHBOARD_DATA",
                        data.toString()
                    )

                    if (data != null) {

                        _uiState.value =
                            _uiState.value.copy(

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

                                predictedLocation =
                                    prediction?.location
                                        ?: "No prediction",

                                predictionConfidence =
                                    prediction?.confidence?.let {
                                        "$it%"
                                    } ?: "--",

                                predictionStatus =
                                    if (prediction?.matched == true)
                                        "MATCHED"
                                    else
                                        "PENDING",

                                prediction = data.prediction,

                                confidence = data.confidence,

                                recentAlert = data.recentAlert,

                                loading = false,

                                error = null
                            )
                    }

                } else {

                    _uiState.value =
                        _uiState.value.copy(
                            loading = false,
                            error = "Dashboard loading failed"
                        )
                }

            } catch (e: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        loading = false,
                        error = e.localizedMessage
                            ?: "Unable to load dashboard"
                    )
            }
        }
    }
}