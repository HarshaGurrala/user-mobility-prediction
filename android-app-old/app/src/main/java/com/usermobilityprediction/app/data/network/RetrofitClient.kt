package com.usermobilityprediction.app.data.network

import android.content.Context
import com.usermobilityprediction.app.data.storage.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // Emulator
    // private const val BASE_URL = "http://10.0.2.2:8000/"

    // Real Phone
//    private const val BASE_URL = "http://192.168.31.114:8000/"
//    private const val BASE_URL = "https://abcd-1234.ngrok-free.app/"

    private const val BASE_URL = "https://worrisome-cataract-tannery.ngrok-free.dev/"

    private fun createClient(
        context: Context
    ): OkHttpClient {

        val tokenManager = TokenManager(context)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(
                AuthInterceptor(tokenManager)
            )
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun authApi(
        context: Context
    ): AuthApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient(context))
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(AuthApi::class.java)
    }

    fun profileApi(
        context: Context
    ): ProfileApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient(context))
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ProfileApi::class.java)
    }

    fun guardianApi(
        context: Context
    ): GuardianApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient(context))
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(GuardianApi::class.java)
    }
}
fun emergencyApi(
    context: Context
): EmergencyApi {

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createClient(context))
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()
        .create(EmergencyApi::class.java)
}