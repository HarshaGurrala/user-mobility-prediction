package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.model.PredictionResponse
import com.usermobilityprediction.app.data.model.PredictionHistoryResponse
import com.usermobilityprediction.app.data.model.PredictionStatisticsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface PredictionApi {


    /*
     * Get latest predicted location
     */

    @GET("prediction/next/{user_id}")
    suspend fun getLatestPrediction(

        @Path("user_id")
        userId: Int

    ):Response<PredictionResponse>



    /*
     * Prediction history
     */

    @GET("prediction/history/{user_id}")
    suspend fun getPredictionHistory(

        @Path("user_id")
        userId: Int

    ): Response<List<PredictionHistoryResponse>>



    /*
     * Prediction statistics
     */

    @GET("prediction/statistics/{user_id}")
    suspend fun getPredictionStatistics(

        @Path("user_id")
        userId: Int

    ): Response<PredictionStatisticsResponse>

}