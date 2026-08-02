package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.data.model.PredictionAnalyticsResponse

@Composable
fun PredictionAnalyticsSection(
    prediction: PredictionAnalyticsResponse?
) {

    if (prediction == null) return

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Prediction Analytics",
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
                title = "Total Predictions",
                value = prediction.totalPredictions.toString(),
                icon = Icons.Default.Analytics
            )

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Matched",
                value = prediction.matchedPredictions.toString(),
                icon = Icons.Default.CheckCircle
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Failed",
                value = prediction.failedPredictions.toString(),
                icon = Icons.Default.Close
            )

            AnalyticsCard(
                modifier = Modifier.weight(1f),
                title = "Avg Confidence",
                value = "${prediction.averageConfidence}%",
                icon = Icons.Default.Speed
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        AnalyticsCard(
            title = "Average Accuracy",
            value = "${String.format("%.2f", prediction.averageAccuracy)}%",
            icon = Icons.Default.Timeline
        )
    }
}