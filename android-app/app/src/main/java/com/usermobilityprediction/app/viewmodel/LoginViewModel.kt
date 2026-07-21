package com.usermobilityprediction.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.repository.AuthRepository
import com.usermobilityprediction.app.data.storage.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class LoginViewModel(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _uiState.value = LoginUiState(isLoading = true)

            try {

                val response = repository.login(
                    email,
                    password
                )

                if (response.isSuccessful && response.body() != null) {

                    val auth = response.body()!!

                    tokenManager.saveToken(
                        auth.accessToken
                    )

                    _uiState.value = LoginUiState(
                        isSuccess = true
                    )

                } else {

                    _uiState.value = LoginUiState(
                        error = "Invalid email or password"
                    )

                }

            } catch (e: Exception) {

                _uiState.value = LoginUiState(
                    error = e.localizedMessage
                )

            }

        }

    }

}