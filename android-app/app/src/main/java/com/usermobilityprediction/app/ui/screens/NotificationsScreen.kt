package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.viewmodel.NotificationViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration


@Composable
fun NotificationsScreen(
    navController: NavController,
    notificationViewModel: NotificationViewModel = viewModel()
) {

    val notifications by notificationViewModel.notifications.collectAsState()
    val loading by notificationViewModel.loading.collectAsState()
    val error by notificationViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        notificationViewModel.loadNotifications()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
    ) {

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {

                Text(
                    text = "Notifications",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "${notificationViewModel.unreadCount()} unread",
                    color = Color(0xFF999999),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // ---------------------------------------------------------
        // LOADING
        // ---------------------------------------------------------

        if (loading) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                CircularProgressIndicator(
                    color = Color(0xFF42A5F5)
                )
            }

            return
        }

        // ---------------------------------------------------------
        // ERROR
        // ---------------------------------------------------------

        if (error != null) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = error ?: "Unable to load notifications",
                    color = Color(0xFFFF6B6B)
                )
            }

            return
        }

        // ---------------------------------------------------------
        // EMPTY
        // ---------------------------------------------------------

        if (notifications.isEmpty()) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color(0xFF666666)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "No notifications",
                    color = Color(0xFF999999)
                )
            }

            return
        }

        // ---------------------------------------------------------
        // NOTIFICATION LIST
        // ---------------------------------------------------------

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(
                items = notifications,
                key = { it.id }
            ) { notification ->

                NotificationItem(
                    notification = notification,
                    onClick = {

                        if (
                            notification.status.equals(
                                "unread",
                                ignoreCase = true
                            )
                        ) {

                            notificationViewModel.markAsRead(
                                notification.id
                            )
                        }
                    }
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }
    }
}


@Composable
private fun NotificationItem(
    notification: com.usermobilityprediction.app.data.model.NotificationResponse,
    onClick: () -> Unit
) {

    val isUnread =
        notification.status.equals(
            "unread",
            ignoreCase = true
        )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = androidx.compose.foundation.shape.RoundedCornerShape(
            20.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor =
                if (isUnread) {
                    Color(0xFF191919)
                } else {
                    Color(0xFF111111)
                }
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // -----------------------------------------------------
            // TITLE + STATUS
            // -----------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint =
                        if (isUnread) {
                            Color(0xFFFF9800)
                        } else {
                            Color(0xFF777777)
                        }
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {

                    Text(
                        text = notification.title,
                        color = Color.White,
                        fontWeight =
                            if (isUnread) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Medium
                            }
                    )

                    Text(
                        text = notification.notification_type,
                        color = Color(0xFF42A5F5),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                if (isUnread) {

                    Text(
                        text = "NEW",
                        color = Color(0xFFFF9800),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            // -----------------------------------------------------
            // ACTUAL ALERT MESSAGE
            // -----------------------------------------------------

            Text(
                text = notification.message,
                color = Color(0xFFD0D0D0),
                style = MaterialTheme.typography.bodyMedium
            )

            // -----------------------------------------------------
            // TIME
            // -----------------------------------------------------

            notification.created_at?.let { createdAt ->

                Text(
                    text = formatNotificationTime(createdAt),
                    color = Color(0xFF777777),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

private fun formatNotificationTime(
    createdAt: String
): String {

    return try {

        val formatter =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME

        val created =
            LocalDateTime.parse(
                createdAt,
                formatter
            )

        val now =
            LocalDateTime.now()

        val seconds =
            Duration.between(
                created,
                now
            ).seconds

        when {

            seconds < 60 ->
                "Just now"

            seconds < 3600 ->
                "${seconds / 60} minutes ago"

            seconds < 86400 ->
                "${seconds / 3600} hours ago"

            seconds < 172800 ->
                "Yesterday, ${
                    created.format(
                        DateTimeFormatter.ofPattern(
                            "h:mm a"
                        )
                    )
                }"

            else ->
                created.format(
                    DateTimeFormatter.ofPattern(
                        "MMM dd, h:mm a"
                    )
                )
        }

    } catch (e: Exception) {

        createdAt
    }
}