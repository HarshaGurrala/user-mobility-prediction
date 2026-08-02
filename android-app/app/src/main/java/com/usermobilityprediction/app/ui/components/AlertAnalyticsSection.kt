package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.weight

import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.data.model.AlertAnalyticsResponse
//import androidx.compose.material.icons.filled.CheckCirclev
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Info


@Composable
fun AlertAnalyticsSection(
    alerts: AlertAnalyticsResponse?
) {

    if (alerts == null) return

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Alert Analytics",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Total Alerts",
                value = alerts.totalAlerts.toString(),
                icon = Icons.Default.Notifications
            )

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Read Alerts",
                value = alerts.readAlerts.toString(),
                icon = Icons.Default.Notifications
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        AnalyticsCard(
            title = "Unread Alerts",
            value = alerts.unreadAlerts.toString(),
            icon =Icons.Default.Info
        )

        if (alerts.alertDistribution.isNotEmpty()) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Alert Distribution",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            alerts.alertDistribution.forEach { item ->

                AnalyticsCard(
                    title = item.type,
                    value = item.count.toString(),
                    icon = Icons.Default.Warning
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }

}