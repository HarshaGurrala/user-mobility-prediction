package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.data.model.AnalyticsOverviewResponse

@Composable
fun AnalyticsOverviewSection(
    overview: AnalyticsOverviewResponse?
) {

    if (overview == null) return

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Overview",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Distance",
                value = "${overview.totalDistance} km",
                icon = Icons.Default.Map
            )

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Locations",
                value = overview.totalLocations.toString(),
                icon = Icons.Default.LocationOn
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Safe Zones",
                value = overview.safeLocations.toString(),
                icon = Icons.Default.Security
            )

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Alerts",
                value = overview.totalAlerts.toString(),
                icon = Icons.Default.Notifications
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Predictions",
                value = overview.totalPredictions.toString(),
                icon = Icons.Default.Psychology
            )

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Success Rate",
                value = overview.predictionSuccessRate?.let {
                    "%.2f%%".format(it)
                } ?: "--",
                icon = Icons.Default.Verified
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        AnalyticsCard(
            title = "Current Safety",
            value = overview.currentSafetyStatus,
            icon = Icons.Default.Security
        )

        Spacer(modifier = Modifier.height(12.dp))

        AnalyticsCard(
            title = "Current Location",
            value = overview.currentLocation,
            icon = Icons.Default.MyLocation
        )
    }
}