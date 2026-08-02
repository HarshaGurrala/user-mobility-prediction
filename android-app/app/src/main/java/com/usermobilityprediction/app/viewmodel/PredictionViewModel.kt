package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.PredictionResponse
import com.usermobilityprediction.app.data.model.PredictionHistoryResponse
import com.usermobilityprediction.app.data.model.PredictionStatisticsResponse
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class PredictionUiState(

    val loading: Boolean = false,

    val error: String? = null,

    val latestPrediction:PredictionResponse? = null,

    val history: List<PredictionHistoryResponse> = emptyList(),

    val statistics: PredictionStatisticsResponse? = null

)



class PredictionViewModel : ViewModel() {


    private val _uiState =
        MutableStateFlow(
            PredictionUiState()
        )


    val uiState: StateFlow<PredictionUiState> =
        _uiState.asStateFlow()



    fun loadPrediction(
        userId: Int
    ) {


        viewModelScope.launch {


            _uiState.value =
                PredictionUiState(
                    loading = true
                )


            try {


                val latestResponse =
                    RetrofitClient.predictionApi
                        .getLatestPrediction(
                            userId
                        )


                val historyResponse =
                    RetrofitClient.predictionApi
                        .getPredictionHistory(
                            userId
                        )


                val statisticsResponse =
                    RetrofitClient.predictionApi
                        .getPredictionStatistics(
                            userId
                        )



                _uiState.value =
                    PredictionUiState(

                        loading = false,


                        latestPrediction =
                            if(
                                latestResponse.isSuccessful
                            )
                                latestResponse.body()
                            else
                                null,


                        history =
                            if(
                                historyResponse.isSuccessful
                            )
                                historyResponse.body()
                                    ?: emptyList()
                            else
                                emptyList(),


                        statistics =
                            if(
                                statisticsResponse.isSuccessful
                            )
                                statisticsResponse.body()
                            else
                                null,


                        error = null
                    )


            } catch(e: Exception){


                _uiState.value =
                    PredictionUiState(

                        loading = false,

                        error =
                            e.localizedMessage
                                ?: "Prediction loading failed"

                    )

            }

        }

    }

}