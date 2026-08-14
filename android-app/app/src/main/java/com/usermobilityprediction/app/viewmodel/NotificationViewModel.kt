package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.NotificationResponse
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {

    private val _notifications =
        MutableStateFlow<List<NotificationResponse>>(emptyList())

    val notifications: StateFlow<List<NotificationResponse>> =
        _notifications

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error

    fun loadNotifications() {

        viewModelScope.launch {

            _loading.value = true
            _error.value = null

            try {

                val response =
                    RetrofitClient.api.getNotifications()

                if (response.isSuccessful) {

                    _notifications.value =
                        response.body() ?: emptyList()

                } else {

                    _error.value =
                        response.errorBody()?.string()
                            ?: "Unable to load notifications"
                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            } finally {

                _loading.value = false
            }
        }
    }

    fun markAsRead(
        notificationId: Int
    ) {

        viewModelScope.launch {

            try {

                val response =
                    RetrofitClient.api.markNotificationRead(
                        notificationId
                    )

                if (response.isSuccessful) {

                    _notifications.value =
                        _notifications.value.map { notification ->

                            if (
                                notification.id ==
                                notificationId
                            ) {

                                notification.copy(
                                    status = "read"
                                )

                            } else {

                                notification
                            }
                        }
                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Unable to mark notification as read"
            }
        }
    }

    fun unreadCount(): Int {

        return _notifications.value.count {
            it.status.equals(
                "unread",
                ignoreCase = true
            )
        }
    }
}