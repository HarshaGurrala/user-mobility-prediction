
package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.usermobilityprediction.app.data.model.AlertAnalyticsResponse
import java.util.Locale

@Composable
fun AlertAnalyticsCard(
    alerts: AlertAnalyticsResponse?
) {

    /*
     * All values come directly from the real backend response.
     *
     * Negative values are safely treated as zero because
     * alert counts cannot be negative.
     */
    val totalAlerts =
        (alerts?.totalAlerts ?: 0)
            .coerceAtLeast(0)

    val readAlerts =
        (alerts?.readAlerts ?: 0)
            .coerceAtLeast(0)

    val unreadAlerts =
        (alerts?.unreadAlerts ?: 0)
            .coerceAtLeast(0)

    /*
     * Calculate Read / Unread percentages using the
     * real total alert count from the backend.
     */
    val readPercentage =
        if (totalAlerts > 0) {
            (readAlerts.toDouble() / totalAlerts.toDouble())
                .coerceIn(0.0, 1.0)
        } else {
            0.0
        }

    val unreadPercentage =
        if (totalAlerts > 0) {
            (unreadAlerts.toDouble() / totalAlerts.toDouble())
                .coerceIn(0.0, 1.0)
        } else {
            0.0
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111111)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            verticalArrangement =
                Arrangement.spacedBy(18.dp)
        ) {

            // ====================================================
            // HEADER
            // ====================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Alert analytics",
                    tint = Color(0xFF42A5F5)
                )

                Column {

                    Text(
                        text = "Alert Analytics",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )

                    Text(
                        text = "Safety alerts and notification insights",
                        color = Color(0xFF777777),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // ====================================================
            // TOTAL ALERTS
            // ====================================================

            Column {

                Text(
                    text = "Total Alerts",
                    color = Color(0xFF9E9E9E),
                    fontSize = 13.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = totalAlerts.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }

            // ====================================================
            // READ + UNREAD CARDS
            // ====================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                AlertMiniStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Read",
                    value = readAlerts.toString(),
                    icon = Icons.Default.CheckCircle,
                    iconColor = Color(0xFF4CAF50)
                )

                AlertMiniStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Unread",
                    value = unreadAlerts.toString(),
                    icon = Icons.Default.Warning,
                    iconColor = Color(0xFFFF9800)
                )
            }

            // ====================================================
            // READ / UNREAD DISTRIBUTION
            // ====================================================

            Column {

                Text(
                    text = "Read / Unread Distribution",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )

                /*
                 * Stacked distribution bar.
                 *
                 * Uses only real backend values.
                 */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .height(12.dp)
                        .background(
                            color = Color(0xFF252525),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {

                    if (readPercentage > 0.0) {

                        Box(
                            modifier = Modifier
                                .weight(
                                    readPercentage.toFloat()
                                )
                                .height(12.dp)
                                .background(
                                    color = Color(0xFF4CAF50),
                                    shape = RoundedCornerShape(
                                        topStart = 8.dp,
                                        bottomStart = 8.dp
                                    )
                                )
                        )
                    }

                    if (unreadPercentage > 0.0) {

                        Box(
                            modifier = Modifier
                                .weight(
                                    unreadPercentage.toFloat()
                                )
                                .height(12.dp)
                                .background(
                                    color = Color(0xFFFF9800),
                                    shape = RoundedCornerShape(
                                        topEnd = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                )
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Text(
                        text = String.format(
                            Locale.US,
                            "Read %.1f%%",
                            readPercentage * 100.0
                        ),
                        color = Color(0xFF81C784),
                        fontSize = 12.sp
                    )

                    Text(
                        text = String.format(
                            Locale.US,
                            "Unread %.1f%%",
                            unreadPercentage * 100.0
                        ),
                        color = Color(0xFFFFB74D),
                        fontSize = 12.sp
                    )
                }
            }

            // ====================================================
            // ALERT DISTRIBUTION
            // ====================================================

            Column {

                Text(
                    text = "Alert Type Distribution",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )

                if (alerts?.alertDistribution.isNullOrEmpty()) {

                    Text(
                        text = "No alert distribution data available",
                        color = Color(0xFF777777),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                } else {

                    Column(
                        modifier = Modifier.padding(top = 14.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(16.dp)
                    ) {

                        alerts.alertDistribution.forEach { item ->

                            /*
                             * Alert counts come from the real backend.
                             * Negative counts are safely treated as zero.
                             */
                            val count =
                                item.count
                                    .coerceAtLeast(0)

                            val distributionPercentage =
                                if (totalAlerts > 0) {
                                    (
                                            count.toDouble() /
                                                    totalAlerts.toDouble()
                                            )
                                        .coerceIn(0.0, 1.0)
                                } else {
                                    0.0
                                }

                            Column {

                                Row(
                                    modifier =
                                        Modifier.fillMaxWidth(),
                                    horizontalArrangement =
                                        Arrangement.SpaceBetween,
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = item.type,
                                        color = Color(0xFFD0D0D0),
                                        fontSize = 13.sp
                                    )

                                    Text(
                                        text = String.format(
                                            Locale.US,
                                            "%d  •  %.1f%%",
                                            count,
                                            distributionPercentage * 100.0
                                        ),
                                        color = Color.White,
                                        fontWeight =
                                            FontWeight.SemiBold,
                                        fontSize = 12.sp
                                    )
                                }

                                // Background track
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 7.dp)
                                        .height(8.dp)
                                        .background(
                                            color = Color(0xFF252525),
                                            shape =
                                                RoundedCornerShape(8.dp)
                                        )
                                ) {

                                    // Dynamic real-data progress
                                    if (distributionPercentage > 0.0) {

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(
                                                    distributionPercentage
                                                        .toFloat()
                                                )
                                                .height(8.dp)
                                                .background(
                                                    color =
                                                        Color(0xFF42A5F5),
                                                    shape =
                                                        RoundedCornerShape(
                                                            8.dp
                                                        )
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertMiniStatCard(
    modifier: Modifier,
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF191919)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor
            )

            Text(
                text = title,
                color = Color(0xFF9E9E9E),
                fontSize = 13.sp
            )

            Text(
                text = value,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}
