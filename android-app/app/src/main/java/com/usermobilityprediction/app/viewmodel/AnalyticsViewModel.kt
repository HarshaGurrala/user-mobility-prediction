
package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.AnalyticsUiState
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnalyticsViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(AnalyticsUiState())

    val uiState: StateFlow<AnalyticsUiState> =
        _uiState.asStateFlow()

    fun loadAnalytics(userId: Int) {

        viewModelScope.launch {

            _uiState.value =
                AnalyticsUiState(
                    loading = true
                )

            try {

                /*
                 * ========================================================
                 * LOAD ALL ANALYTICS ENDPOINTS
                 * ========================================================
                 *
                 * Every endpoint is handled independently.
                 *
                 * If one endpoint fails, the other successful
                 * backend responses are still displayed.
                 *
                 * No mock data is introduced.
                 */

                val overviewDeferred =
                    async {
                        runCatching {
                            RetrofitClient.api
                                .getAnalyticsOverview(userId)
                        }.getOrNull()
                    }

                val dailyDeferred =
                    async {
                        runCatching {
                            RetrofitClient.api
                                .getDailyDistance(userId)
                        }.getOrNull()
                    }

                val weeklyDeferred =
                    async {
                        runCatching {
                            RetrofitClient.api
                                .getWeeklyDistance(userId)
                        }.getOrNull()
                    }

                val predictionDeferred =
                    async {
                        runCatching {
                            RetrofitClient.api
                                .getPredictionAnalytics(userId)
                        }.getOrNull()
                    }

                val safetyDeferred =
                    async {
                        runCatching {
                            RetrofitClient.api
                                .getSafetyAnalytics(userId)
                        }.getOrNull()
                    }

                val alertDeferred =
                    async {
                        runCatching {
                            RetrofitClient.api
                                .getAlertAnalytics(userId)
                        }.getOrNull()
                    }

                val safeZoneDeferred =
                    async {
                        runCatching {
                            RetrofitClient.api
                                .getSafeZoneAnalytics(userId)
                        }.getOrNull()
                    }


                /*
                 * ========================================================
                 * WAIT FOR ALL REQUESTS
                 * ========================================================
                 */

                val overviewResponse =
                    overviewDeferred.await()

                val dailyResponse =
                    dailyDeferred.await()

                val weeklyResponse =
                    weeklyDeferred.await()

                val predictionResponse =
                    predictionDeferred.await()

                val safetyResponse =
                    safetyDeferred.await()

                val alertResponse =
                    alertDeferred.await()

                val safeZoneResponse =
                    safeZoneDeferred.await()


                val nextPredictionDeferred =
                    async {
                        runCatching {
                            RetrofitClient.api
                                .getNextPrediction(userId)
                        }.getOrNull()
                    }


                /*
                 * ========================================================
                 * UPDATE UI WITH REAL BACKEND DATA
                 * ========================================================
                 *
                 * Successful responses are displayed.
                 *
                 * Failed individual endpoints safely return:
                 *
                 * - null for object responses
                 * - emptyList() for list responses
                 *
                 * This prevents one failed analytics endpoint
                 * from breaking the entire Analytics page.
                 */

                _uiState.value =
                    AnalyticsUiState(

                        loading = false,

                        overview =
                            if (
                                overviewResponse
                                    ?.isSuccessful == true
                            ) {
                                overviewResponse.body()
                            } else {
                                null
                            },

                        dailyDistance =
                            if (
                                dailyResponse
                                    ?.isSuccessful == true
                            ) {
                                dailyResponse.body()
                                    ?: emptyList()
                            } else {
                                emptyList()
                            },

                        weeklyDistance =
                            if (
                                weeklyResponse
                                    ?.isSuccessful == true
                            ) {
                                weeklyResponse.body()
                                    ?: emptyList()
                            } else {
                                emptyList()
                            },

                        prediction =
                            if (
                                predictionResponse
                                    ?.isSuccessful == true
                            ) {
                                predictionResponse.body()
                            } else {
                                null
                            },

                        safety =
                            if (
                                safetyResponse
                                    ?.isSuccessful == true
                            ) {
                                safetyResponse.body()
                            } else {
                                null
                            },

                        alerts =
                            if (
                                alertResponse
                                    ?.isSuccessful == true
                            ) {
                                alertResponse.body()
                            } else {
                                null
                            },

                        safeZones =
                            if (
                                safeZoneResponse
                                    ?.isSuccessful == true
                            ) {
                                safeZoneResponse.body()
                            } else {
                                null
                            },

                        error = null
                    )

            } catch (e: Exception) {

                /*
                 * ========================================================
                 * GLOBAL ERROR
                 * ========================================================
                 *
                 * Handles unexpected failures outside the individual
                 * endpoint request handling.
                 */

                _uiState.value =
                    AnalyticsUiState(

                        loading = false,

                        error =
                            e.localizedMessage
                                ?: "Failed to load analytics."
                    )
            }
        }
    }
}

