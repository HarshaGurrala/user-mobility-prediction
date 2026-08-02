
package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.data.model.SafetyAnalyticsResponse
import java.util.Locale

@Composable
fun SafetyAnalyticsCard(
    safety: SafetyAnalyticsResponse?
) {

    val safe =
        (safety?.safeEvents ?: 0)
            .coerceAtLeast(0)

    val warning =
        (safety?.warningEvents ?: 0)
            .coerceAtLeast(0)

    val unknown =
        (safety?.unknownEvents ?: 0)
            .coerceAtLeast(0)

    val total =
        (safety?.totalEvents ?: 0)
            .coerceAtLeast(0)

    /*
     * Use the backend totalEvents as the source of truth
     * for percentage calculations.
     */
    val safePercentage =
        if (total > 0) {
            (safe.toDouble() / total.toDouble() * 100.0)
                .coerceIn(0.0, 100.0)
        } else {
            0.0
        }

    val warningPercentage =
        if (total > 0) {
            (warning.toDouble() / total.toDouble() * 100.0)
                .coerceIn(0.0, 100.0)
        } else {
            0.0
        }

    val unknownPercentage =
        if (total > 0) {
            (unknown.toDouble() / total.toDouble() * 100.0)
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
                imageVector = Icons.Default.Security,
                contentDescription = "Safety analytics",
                tint = Color(0xFF3FA9FF)
            )

            Text(
                text = "Safety Analytics",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Text(
            text = "Movement safety event distribution",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF71859A),
            modifier = Modifier.padding(top = 5.dp)
        )

        // Total events
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement =
                Arrangement.SpaceBetween,
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = "Total Safety Events",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFB7C7D8)
            )

            Text(
                text = total.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        /*
         * Stacked distribution bar.
         *
         * Each segment is calculated from real backend data.
         * If there are no events, an empty track is shown.
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp)
                .height(14.dp)
                .background(
                    color = Color(0xFF182431),
                    shape = RoundedCornerShape(14.dp)
                )
        ) {

            if (total > 0) {

                if (safePercentage > 0.0) {
                    Row(
                        modifier = Modifier
                            .weight(safePercentage.toFloat())
                            .height(14.dp)
                            .background(
                                color = Color(0xFF20C878),
                                shape = RoundedCornerShape(
                                    topStart = 14.dp,
                                    bottomStart = 14.dp
                                )
                            )
                    ) {}
                }

                if (warningPercentage > 0.0) {
                    Row(
                        modifier = Modifier
                            .weight(warningPercentage.toFloat())
                            .height(14.dp)
                            .background(
                                color = Color(0xFFFFB020)
                            )
                    ) {}
                }

                if (unknownPercentage > 0.0) {
                    Row(
                        modifier = Modifier
                            .weight(unknownPercentage.toFloat())
                            .height(14.dp)
                            .background(
                                color = Color(0xFFFF5C5C),
                                shape = RoundedCornerShape(
                                    topEnd = 14.dp,
                                    bottomEnd = 14.dp
                                )
                            )
                    ) {}
                }
            }
        }

        // Safety event breakdown
        SafetyBar(
            title = "Safe Events",
            count = safe,
            percentage = safePercentage,
            color = Color(0xFF20C878)
        )

        SafetyBar(
            title = "Warning Events",
            count = warning,
            percentage = warningPercentage,
            color = Color(0xFFFFB020)
        )

        SafetyBar(
            title = "Unknown Events",
            count = unknown,
            percentage = unknownPercentage,
            color = Color(0xFFFF5C5C)
        )
    }
}

@Composable
private fun SafetyBar(
    title: String,
    count: Int,
    percentage: Double,
    color: Color
) {

    Column(
        modifier = Modifier.padding(top = 18.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween,
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .padding(end = 8.dp)
                        .background(
                            color = color,
                            shape = RoundedCornerShape(4.dp)
                        )
                )

                Text(
                    text = title,
                    color = Color(0xFFB7C7D8),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = String.format(
                    Locale.US,
                    "%d  •  %.1f%%",
                    count,
                    percentage
                ),
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
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

            if (percentage > 0.0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(
                            (percentage / 100.0)
                                .toFloat()
                                .coerceIn(0f, 1f)
                        )
                        .height(10.dp)
                        .background(
                            color = color,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {}
            }
        }
    }
}

