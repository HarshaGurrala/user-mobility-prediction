package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.usermobilityprediction.app.data.model.ChangePasswordRequest
import com.usermobilityprediction.app.data.repository.AuthRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch


class ChangePasswordViewModel : ViewModel() {


    private val repository =
        AuthRepository()


    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading


    private val _success =
        MutableStateFlow(false)

    val success: StateFlow<Boolean> =
        _success


    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error


    fun changePassword(

        currentPassword: String,

        newPassword: String

    ) {

        viewModelScope.launch {


            _loading.value =
                true

            _success.value =
                false

            _error.value =
                null


            try {


                val response =
                    repository.changePassword(

                        ChangePasswordRequest(

                            current_password =
                                currentPassword,

                            new_password =
                                newPassword
                        )
                    )


                if (
                    response.isSuccessful
                ) {


                    _success.value =
                        true


                } else {


                    _error.value =
                        extractErrorMessage(

                            response.code(),

                            response.errorBody()
                                ?.string()
                        )
                }


            } catch (e: Exception) {


                _error.value =
                    e.localizedMessage
                        ?: "Unable to connect to server"


            } finally {


                _loading.value =
                    false
            }
        }
    }


    fun resetState() {

        _success.value =
            false

        _error.value =
            null
    }


    private fun extractErrorMessage(

        statusCode: Int,

        errorBody: String?

    ): String {


        if (
            statusCode == 400 &&
            errorBody?.contains(
                "Current password is incorrect"
            ) == true
        ) {

            return "Current password is incorrect"
        }


        if (
            statusCode == 400 &&
            errorBody?.contains(
                "New password must be different"
            ) == true
        ) {

            return "New password must be different from your current password"
        }


        return "Failed to change password"
    }
}
//package com.usermobilityprediction.app.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//
//import com.usermobilityprediction.app.data.model.ChangePasswordRequest
//import com.usermobilityprediction.app.data.repository.AuthRepository
//
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//
//class ChangePasswordViewModel : ViewModel() {
//
//    private val repository =
//        AuthRepository()
//
//
//    private val _loading =
//        MutableStateFlow(false)
//
//    val loading: StateFlow<Boolean> =
//        _loading
//
//
//    private val _success =
//        MutableStateFlow(false)
//
//    val success: StateFlow<Boolean> =
//        _success
//
//
//    private val _error =
//        MutableStateFlow<String?>(null)
//
//    val error: StateFlow<String?> =
//        _error
//
//
//    fun changePassword(
//        currentPassword: String,
//        newPassword: String
//    ) {
//
//        viewModelScope.launch {
//
//            _loading.value = true
//
//            _success.value = false
//
//            _error.value = null
//
//            try {
//
//                val response =
//                    repository.changePassword(
//                        ChangePasswordRequest(
//                            current_password =
//                                currentPassword,
//                            new_password =
//                                newPassword
//                        )
//                    )
//
//                if (response.isSuccessful) {
//
//                    _success.value = true
//
//                } else {
//
//                    val errorBody =
//                        response.errorBody()
//                            ?.string()
//
//                    _error.value =
//                        extractErrorMessage(
//                            response.code(),
//                            errorBody
//                        )
//                }
//
//            } catch (e: Exception) {
//
//                _error.value =
//                    e.localizedMessage
//                        ?: "Unable to connect to server"
//
//            } finally {
//
//                _loading.value = false
//            }
//        }
//    }
//
//
//    fun resetState() {
//
//        _success.value = false
//
//        _error.value = null
//    }
//
//
//    private fun extractErrorMessage(
//        statusCode: Int,
//        errorBody: String?
//    ): String {
//
//        return when {
//
//            statusCode == 401 ->
//                "Authentication failed. Please log in again."
//
//            statusCode == 400 &&
//                    errorBody?.contains(
//                        "Current password is incorrect",
//                        ignoreCase = true
//                    ) == true ->
//                "Current password is incorrect"
//
//            statusCode == 400 &&
//                    errorBody?.contains(
//                        "New password must be different",
//                        ignoreCase = true
//                    ) == true ->
//                "New password must be different from your current password"
//
//            statusCode == 404 ->
//                "Password change endpoint was not found"
//
//            else ->
//                "Failed to change password. HTTP $statusCode\n$errorBody"
//        }
//    }
//}