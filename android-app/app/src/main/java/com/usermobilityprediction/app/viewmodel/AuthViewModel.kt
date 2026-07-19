package com.usermobilityprediction.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.models.RegisterRequest
import com.usermobilityprediction.app.data.repository.MockAuthRepository
import com.usermobilityprediction.app.data.storage.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String = "") : AuthState()
    data class Error(val error: String) : AuthState()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val tokenManager = TokenManager(application.applicationContext)
    private val authRepo = MockAuthRepository(application.applicationContext)

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val token = authRepo.login(email, password)
                if (token == null) {
                    _state.value = AuthState.Error("Invalid credentials")
                } else {
                    tokenManager.saveToken(token)
                    _state.value = AuthState.Success("logged_in")
                }
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.localizedMessage ?: "Internal error")
            }
        }
    }

    fun register(fullName: String, email: String, phone: String?, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val ok = authRepo.register(fullName, email, phone, password)
                if (!ok) {
                    _state.value = AuthState.Error("Invalid registration data")
                } else {
                    // auto-login after registration
                    val token = authRepo.login(email, password)
                    token?.let { tokenManager.saveToken(it) }
                    _state.value = AuthState.Success("registered")
                }
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.localizedMessage ?: "Internal error")
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }

    fun logout() {
        tokenManager.clear()
        authRepo.clear()
        _state.value = AuthState.Success("logged_out")
    }
}
