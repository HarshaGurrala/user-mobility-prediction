package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.weight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.data.model.SafetyAnalyticsResponse

@Composable
fun SafetyAnalyticsSection(
    safety: SafetyAnalyticsResponse?
) {

    if (safety == null) return

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Safety Analytics",
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
                title = "Safe Events",
                value = safety.safeEvents.toString(),
                icon = Icons.Default.CheckCircle
            )

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Warning Events",
                value = safety.warningEvents.toString(),
                icon = Icons.Default.Warning
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                AnalyticsCard(
                    modifier = Modifier.weight(1f),
                    title = "Unknown Events",
                    value = safety.unknownEvents.toString(),
                    icon = Icons.Default.Error
                )

                AnalyticsCard(
                    modifier = Modifier.weight(1f),
                    title = "Total Events",
                    value = safety.totalEvents.toString(),
                    icon = Icons.Default.Info
                )
            }

        }

    }

}