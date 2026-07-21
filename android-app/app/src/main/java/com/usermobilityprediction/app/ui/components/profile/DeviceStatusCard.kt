package com.usermobilityprediction.app.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeviceStatusCard(
    locationName: String?,
    latitude: Double?,
    longitude: Double?,
    gpsEnabled: Boolean?,
    lastUpdated: String?,
    deviceName: String?,
    androidVersion: String?
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0x33219BFF),
                        Color(0x11000000)
                    )
                ),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(22.dp)
    ) {

        Text(
            text = "Device Status",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        DeviceInfoRow(
            icon = Icons.Outlined.LocationOn,
            title = "Current Location",
            value = locationName
        )

        DividerLine()

        DeviceInfoRow(
            icon = Icons.Outlined.MyLocation,
            title = "Latitude",
            value = latitude?.toString()
        )

        DividerLine()

        DeviceInfoRow(
            icon = Icons.Outlined.MyLocation,
            title = "Longitude",
            value = longitude?.toString()
        )

        DividerLine()

        DeviceInfoRow(
            icon = Icons.Outlined.Sensors,
            title = "GPS Status",
            value = when (gpsEnabled) {
                true -> "Active"
                false -> "Disabled"
                null -> null
            }
        )

        DividerLine()

        DeviceInfoRow(
            icon = Icons.Outlined.Schedule,
            title = "Last Updated",
            value = lastUpdated
        )

        DividerLine()

        DeviceInfoRow(
            icon = Icons.Outlined.PhoneAndroid,
            title = "Device",
            value = deviceName
        )

        DividerLine()

        DeviceInfoRow(
            icon = Icons.Outlined.Memory,
            title = "Android Version",
            value = androidVersion
        )
    }
}

@Composable
private fun DeviceInfoRow(
    icon: ImageVector,
    title: String,
    value: String?
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4DB8FF),
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column {

            Text(
                text = title,
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value ?: "Not available",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun DividerLine() {

    Spacer(modifier = Modifier.height(16.dp))

    HorizontalDivider(
        color = Color.White.copy(alpha = 0.08f)
    )

    Spacer(modifier = Modifier.height(16.dp))
}