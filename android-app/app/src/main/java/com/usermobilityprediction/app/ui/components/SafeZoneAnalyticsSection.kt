package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.data.model.SafeZoneAnalyticsResponse

@Composable
fun SafeZoneAnalyticsSection(
    safeZones: SafeZoneAnalyticsResponse?
) {

    if (safeZones == null) return

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Safe Zone Analytics",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnalyticsCard(
            title = "Total Safe Zones",
            value = safeZones.totalSafeZones.toString(),
            icon = Icons.Default.Home
        )


        Spacer(modifier = Modifier.height(16.dp))

        safeZones.zones.forEach { zone ->

            AnalyticsCard(
                title = zone.locationName,
                value = "Radius : ${zone.radius} m",
                icon = Icons.Default.Place
            )

            Spacer(modifier = Modifier.height(8.dp))

            AnalyticsCard(
                title = "Latitude",
                value = zone.latitude.toString(),
                icon = Icons.Default.LocationOn
            )

            Spacer(modifier = Modifier.height(8.dp))

            AnalyticsCard(
                title = "Longitude",
                value = zone.longitude.toString(),
                icon = Icons.Default.MyLocation
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

    }

}