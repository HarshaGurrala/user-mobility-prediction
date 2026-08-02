package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import com.usermobilityprediction.app.data.model.SafeZoneAnalyticsResponse

@Composable
fun SafeZoneAnalyticsCard(
    safeZone: SafeZoneAnalyticsResponse?
) {

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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            /*
             * ====================================================
             * HEADER
             * ====================================================
             */

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF42A5F5),
                    modifier = Modifier.size(28.dp)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column {

                    Text(
                        text = "Safe Zones",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Your trusted locations",
                        color = Color(0xFF8FA4B8)
                    )

                }

            }


            /*
             * ====================================================
             * TOTAL SAFE ZONES
             * ====================================================
             */

            Text(
                text = "Total Safe Zones: ${safeZone?.totalSafeZones ?: 0}",
                color = Color(0xFF64B5F6),
                fontWeight = FontWeight.SemiBold
            )


            /*
             * ====================================================
             * SAFE ZONE LIST
             * ====================================================
             */

            if (safeZone?.zones.isNullOrEmpty()) {

                Text(
                    text = "No safe zones available",
                    color = Color(0xFF888888)
                )

            } else {

                safeZone!!.zones.forEach { zone ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF191919)
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            /*
                             * Location icon
                             */

                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFF42A5F5),
                                modifier = Modifier.size(26.dp)
                            )

                            Spacer(
                                modifier = Modifier.width(14.dp)
                            )


                            /*
                             * Location name + radius
                             *
                             * IMPORTANT:
                             * Latitude and longitude are
                             * intentionally NOT displayed.
                             */

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement =
                                    Arrangement.spacedBy(5.dp)
                            ) {

                                Text(
                                    text = zone.locationName,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Safe Zone",
                                    color = Color(0xFF8FA4B8)
                                )

                            }


                            /*
                             * Radius
                             */

                            Column(
                                horizontalAlignment =
                                    Alignment.End
                            ) {

                                Text(
                                    text = "Radius",
                                    color = Color(0xFF8FA4B8)
                                )

                                Text(
                                    text = "${zone.radius} m",
                                    color = Color(0xFF64B5F6),
                                    fontWeight = FontWeight.Bold
                                )

                            }

                        }

                    }

                }

            }

        }

    }

}