
package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.data.model.WeeklyDistanceResponse
import java.util.Locale

import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun WeeklyDistanceSection(
    weeklyDistance: List<WeeklyDistanceResponse>
) {

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
                imageVector = Icons.Default.DateRange,
                contentDescription = "Weekly travel",
                tint = Color(0xFF3FA9FF)
            )

            Text(
                text = "Weekly Travel",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Text(
            text = "Distance travelled by week",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF71859A),
            modifier = Modifier.padding(top = 4.dp)
        )

        if (weeklyDistance.isNotEmpty()) {

            val safeWeeklyDistance =
                weeklyDistance.map {
                    it.distance.coerceAtLeast(0.0)
                }

            val maxDistance =
                safeWeeklyDistance.maxOrNull()
                    ?.coerceAtLeast(1.0)
                    ?: 1.0

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .padding(top = 24.dp)
            ) {

                val graphWidth = size.width
                val graphHeight = size.height

                val spacing =
                    if (safeWeeklyDistance.size > 1)
                        graphWidth / (safeWeeklyDistance.size - 1)
                    else
                        graphWidth

                // Horizontal guide lines
                repeat(5) { i ->

                    val y =
                        graphHeight -
                                (graphHeight / 4f) * i

                    drawLine(
                        color = Color(0xFF1D2B38),
                        start = Offset(0f, y),
                        end = Offset(graphWidth, y),
                        strokeWidth = 2f
                    )
                }

                val path = Path()

                safeWeeklyDistance.forEachIndexed { index, value ->

                    val x = spacing * index

                    val y =
                        graphHeight -
                                ((value / maxDistance).toFloat() * graphHeight)

                    if (index == 0)
                        path.moveTo(x, y)
                    else
                        path.lineTo(x, y)
                }

                drawPath(
                    path = path,
                    color = Color(0xFF42A5F5),
                    style = Stroke(
                        width = 6f,
                        cap = StrokeCap.Round
                    )
                )

                safeWeeklyDistance.forEachIndexed { index, value ->

                    val x = spacing * index

                    val y =
                        graphHeight -
                                ((value / maxDistance).toFloat() * graphHeight)

                    drawCircle(
                        color = Color(0xFF42A5F5),
                        radius = 9f,
                        center = Offset(x, y)
                    )

                    drawCircle(
                        color = Color.White,
                        radius = 4f,
                        center = Offset(x, y)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                weeklyDistance.forEach {

                    Text(
                        text = it.week,
                        color = Color(0xFF8FA4B8),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

        }

        if (weeklyDistance.isEmpty()) {

            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 32.dp,
                        bottom = 24.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color(0xFF4B6075),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "No weekly travel data available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF8A9BAD),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Weekly travel activity will appear here once location data is recorded.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF5F7182),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

        } else {

            /*
             * Use only real backend data.
             *
             * Negative distance values are treated as zero
             * because travelled distance cannot be negative.
             */
            val safeWeeklyDistance = weeklyDistance.map { item ->
                item to item.distance.coerceAtLeast(0.0)
            }

            val maxDistance =
                safeWeeklyDistance
                    .maxOfOrNull { (_, distance) -> distance }
                    ?.coerceAtLeast(0.1)
                    ?: 0.1

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),

                verticalArrangement =
                    Arrangement.spacedBy(18.dp)
            ) {

                safeWeeklyDistance.forEach { (item, distance) ->

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        // Week and actual distance
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.SpaceBetween,
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Text(
                                text = item.week,
                                color = Color(0xFFB7C7D8),
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1
                            )

                            Text(
                                text = String.format(
                                    Locale.US,
                                    "%.1f km",
                                    distance
                                ),
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        // Background track
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 8.dp)
//                                .height(12.dp)
//                                .background(
//                                    color = Color(0xFF182431),
//                                    shape = RoundedCornerShape(12.dp)
//                                )
//                        ) {
//
//                            // Dynamic progress based on
//                            // the largest real backend value
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth(
//                                        if (distance > 0.0) {
//                                            (distance / maxDistance)
//                                                .toFloat()
//                                                .coerceIn(0.02f, 1f)
//                                        } else {
//                                            0.02f
//                                        }
//                                    )
//                                    .height(12.dp)
//                                    .background(
//                                        color = Color(0xFF1688FF),
//                                        shape = RoundedCornerShape(12.dp)
//                                    )
//                            ) {}
//                        }
                    }
                }
            }
        }
    }
}
