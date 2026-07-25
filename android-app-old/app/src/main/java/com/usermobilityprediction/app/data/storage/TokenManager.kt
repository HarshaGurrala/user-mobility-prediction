package com.usermobilityprediction.app.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(
    name = "auth_preferences"
)

class TokenManager(
    private val context: Context
) {

    companion object {
        private val ACCESS_TOKEN =
            stringPreferencesKey("access_token")
    }

    val token: Flow<String?> =
        context.dataStore.data.map {
            it[ACCESS_TOKEN]
        }

    suspend fun saveToken(
        token: String
    ) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit {
            it.remove(ACCESS_TOKEN)
        }
    }

    fun getToken(): String? =
        runBlocking {
            token.firstOrNull()
        }

    fun clear() =
        runBlocking {
            clearToken()
        }
}