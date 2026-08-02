package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.model.AlertAnalyticsResponse
import com.usermobilityprediction.app.data.model.PredictionAnalyticsResponse
import com.usermobilityprediction.app.data.model.SafeZoneAnalyticsResponse
import com.usermobilityprediction.app.data.model.SafetyAnalyticsResponse
import com.usermobilityprediction.app.data.model.AnalyticsOverviewResponse
import com.usermobilityprediction.app.data.model.DailyDistanceResponse
import com.usermobilityprediction.app.data.model.WeeklyDistanceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface AnalyticsApi {


    @GET("analytics/overview/{user_id}")
    suspend fun getAnalyticsOverview(
        @Path("user_id") userId: Int
    ): Response<AnalyticsOverviewResponse>


    @GET("analytics/daily-distance/{user_id}")
    suspend fun getDailyDistance(
        @Path("user_id") userId: Int
    ): Response<List<DailyDistanceResponse>>


    @GET("analytics/weekly-distance/{user_id}")
    suspend fun getWeeklyDistance(
        @Path("user_id") userId: Int
    ): Response<List<WeeklyDistanceResponse>>


    @GET("analytics/predictions/{user_id}")
    suspend fun getPredictionAnalytics(
        @Path("user_id") userId: Int
    ): Response<PredictionAnalyticsResponse>


    @GET("analytics/safety/{user_id}")
    suspend fun getSafetyAnalytics(
        @Path("user_id") userId: Int
    ): Response<SafetyAnalyticsResponse>


    @GET("analytics/alerts/{user_id}")
    suspend fun getAlertAnalytics(
        @Path("user_id") userId: Int
    ): Response<AlertAnalyticsResponse>


    @GET("analytics/safe-zones/{user_id}")
    suspend fun getSafeZoneAnalytics(
        @Path("user_id") userId: Int
    ): Response<SafeZoneAnalyticsResponse>

}