package com.usermobilityprediction.app.data.network

import android.content.Context
import com.usermobilityprediction.app.data.storage.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {




    const val BASE_URL =
        "https://worrisome-cataract-tannery.ngrok-free.dev/"


    private lateinit var tokenManager: TokenManager



    fun initialize(context: Context) {

        tokenManager =
            TokenManager(
                context.applicationContext
            )

    }





    private val logging =
        HttpLoggingInterceptor().apply {

            level =
                HttpLoggingInterceptor.Level.BODY

        }



    private val authInterceptor =
        Interceptor { chain ->


            val originalRequest =
                chain.request()


            val token =
                if (::tokenManager.isInitialized) {

                    tokenManager.getToken()

                } else {

                    null

                }


            android.util.Log.d(
                "AUTH_TEST",
                "TOKEN = $token"
            )


            val requestBuilder =
                originalRequest
                    .newBuilder()



            if (!token.isNullOrBlank()) {

                requestBuilder
                    .addHeader(
                        "Authorization",
                        "Bearer $token"
                    )

            }


            chain.proceed(
                requestBuilder.build()
            )

        }



    private val client =
        OkHttpClient.Builder()

            .addInterceptor(
                authInterceptor
            )

            .addInterceptor(
                logging
            )

            .build()



    private val retrofit: Retrofit by lazy {


        Retrofit.Builder()

            .baseUrl(
                BASE_URL
            )

            .client(
                client
            )

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()

    }



    val api: ApiService by lazy {

        retrofit.create(
            ApiService::class.java
        )

    }



    val predictionApi: PredictionApi by lazy {

        retrofit.create(
            PredictionApi::class.java
        )

    }


    val emergencyContactApi: EmergencyContactApi by lazy {

        retrofit.create(EmergencyContactApi::class.java)

    }

    val safeLocationApi: SafeLocationApi by lazy {

        retrofit.create(SafeLocationApi::class.java)

    }



}