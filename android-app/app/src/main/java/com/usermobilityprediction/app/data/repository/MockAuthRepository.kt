package com.usermobilityprediction.app.data.repository

import android.content.Context
import kotlinx.coroutines.delay

class MockAuthRepository(private val context: Context) {

    private val prefs by lazy { context.getSharedPreferences("mock_auth", Context.MODE_PRIVATE) }

    suspend fun login(email: String, password: String): String? {
        delay(400)
        if (email.isBlank() || password.length < 4) return null
        // allow any valid credentials for frontend mock
        val token = "mock-token-${System.currentTimeMillis()}"
        prefs.edit().putString("mock_token", token).putString("mock_email", email).apply()
        return token
    }

    suspend fun register(fullName: String, email: String, phone: String?, password: String): Boolean {
        delay(600)
        if (fullName.isBlank() || email.isBlank() || password.length < 4) return false
        prefs.edit()
            .putString("mock_full_name", fullName)
            .putString("mock_email", email)
            .putString("mock_phone", phone)
            .apply()
        return true
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun getStoredEmail(): String? = prefs.getString("mock_email", null)
}
