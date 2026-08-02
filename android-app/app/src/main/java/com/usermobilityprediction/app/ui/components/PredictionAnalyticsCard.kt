
package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.data.model.PredictionAnalyticsResponse
import java.util.Locale

@Composable
fun PredictionAnalyticsCard(
    prediction: PredictionAnalyticsResponse?
) {

    val totalPredictions =
        (prediction?.totalPredictions ?: 0)
            .coerceAtLeast(0)

    val matchedPredictions =
        (prediction?.matchedPredictions ?: 0)
            .coerceAtLeast(0)

    val failedPredictions =
        (prediction?.failedPredictions ?: 0)
            .coerceAtLeast(0)

    val accuracy =
        (prediction?.averageAccuracy ?: 0.0)
            .coerceIn(0.0, 100.0)

    val confidence =
        (prediction?.averageConfidence ?: 0.0)
            .coerceIn(0.0, 100.0)

    /*
     * Calculated from the real prediction counts.
     *
     * This is intentionally separate from averageAccuracy,
     * because the backend may calculate average accuracy
     * differently from the matched/failed prediction ratio.
     */
    val successRate =
        if (totalPredictions > 0) {
            (matchedPredictions.toDouble() / totalPredictions.toDouble())
                .times(100.0)
                .coerceIn(0.0, 100.0)
        } else {
            0.0
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF101820),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {

        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = "AI prediction analytics",
                tint = Color(0xFF3FA9FF)
            )

            Text(
                text = "AI Prediction Analytics",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Text(
            text = "Machine learning prediction performance",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF71859A),
            modifier = Modifier.padding(top = 5.dp)
        )

        // Prediction count metrics
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {

            PredictionMetric(
                modifier = Modifier.weight(1f),
                title = "Predictions",
                value = totalPredictions.toString()
            )

            PredictionMetric(
                modifier = Modifier.weight(1f),
                title = "Matched",
                value = matchedPredictions.toString()
            )

            PredictionMetric(
                modifier = Modifier.weight(1f),
                title = "Failed",
                value = failedPredictions.toString()
            )
        }

        // Calculated prediction success rate
        PredictionProgress(
            title = "Prediction Success Rate",
            value = successRate
        )

        // Backend-provided average accuracy
        PredictionProgress(
            title = "Prediction Accuracy",
            value = accuracy
        )

        // Backend-provided average confidence
        PredictionProgress(
            title = "Average Confidence",
            value = confidence
        )
    }
}

@Composable
private fun PredictionMetric(
    modifier: Modifier,
    title: String,
    value: String
) {

    Column(
        modifier = modifier
            .background(
                color = Color(0xFF0A141E),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp)
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF71859A),
            maxLines = 1
        )

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
private fun PredictionProgress(
    title: String,
    value: Double
) {

    val progress =
        (value / 100.0)
            .toFloat()
            .coerceIn(0f, 1f)

    Column(
        modifier = Modifier.padding(top = 20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween,
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = title,
                color = Color(0xFFB7C7D8),
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = String.format(
                    Locale.US,
                    "%.1f%%",
                    value
                ),
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(10.dp)
                .background(
                    color = Color(0xFF182431),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(10.dp)
                    .background(
                        color = Color(0xFF1688FF),
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {}
        }
    }
}