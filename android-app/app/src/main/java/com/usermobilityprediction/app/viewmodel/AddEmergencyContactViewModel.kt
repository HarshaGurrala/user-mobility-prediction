//package com.usermobilityprediction.app.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.usermobilityprediction.app.data.model.EmergencyContactCreateRequest
//import com.usermobilityprediction.app.data.network.RetrofitClient
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class AddEmergencyContactViewModel : ViewModel() {
//
//    private val _loading = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> = _loading.asStateFlow()
//
//    private val _success = MutableStateFlow(false)
//    val success: StateFlow<Boolean> = _success.asStateFlow()
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error.asStateFlow()
//
//    fun addContact(
//        userId: Int,
//        request: EmergencyContactCreateRequest
//    ) {
//
//        viewModelScope.launch {
//
//            try {
//
//                _loading.value = true
//                _error.value = null
//
//                val response =
//                    RetrofitClient
//                        .emergencyContactApi
//                        .addContact(
//                            userId,
//                            request
//                        )
//
//                if (response.isSuccessful) {
//
//                    _success.value = true
//
//                } else {
//
//                    _error.value = "Unable to add contact"
//
//                }
//
//            } catch (e: Exception) {
//
//                _error.value =
//                    e.localizedMessage ?: "Network Error"
//
//            } finally {
//
//                _loading.value = false
//
//            }
//
//        }
//
//    }
//
//}