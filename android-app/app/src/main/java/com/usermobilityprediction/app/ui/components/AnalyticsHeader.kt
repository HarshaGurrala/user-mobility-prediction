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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AnalyticsHeader(

    userName: String,

    lastUpdated: String,

    safetyStatus: String,

    currentLocation: String

) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(28.dp),

        colors =
            CardDefaults.cardColors(

                containerColor =
                    Color(0xFF071321)

            )

    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF071321)
                    )
                    .padding(24.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)

        ) {


            /*
             * ====================================================
             * TITLE
             * ====================================================
             */

            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Icon(

                    imageVector =
                        Icons.Default.BarChart,

                    contentDescription =
                        null,

                    tint =
                        Color(0xFF2196F3),

                    modifier =
                        Modifier.size(42.dp)

                )

                Spacer(
                    modifier =
                        Modifier.width(14.dp)
                )

                Column {

                    Text(

                        text =
                            "Mobility Analytics",

                        style =
                            MaterialTheme.typography.headlineMedium,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.White

                    )

                    Text(

                        text =
                            "Real-time safety & movement insights",

                        style =
                            MaterialTheme.typography.bodyMedium,

                        color =
                            Color(0xFF8FA4B8)

                    )

                }

            }


            /*
             * ====================================================
             * USER
             * ====================================================
             */

            Column {

                Text(

                    text =
                        "Welcome back",

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        Color(0xFF8FA4B8)

                )

                Text(

                    text =
                        userName,

                    style =
                        MaterialTheme.typography.titleLarge,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Color.White

                )

            }


            /*
             * ====================================================
             * SAFETY + LOCATION
             * ====================================================
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {


                /*
                 * SAFETY STATUS
                 */

                Card(

                    modifier =
                        Modifier.weight(1f),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color(0xFF17221F)

                        )

                ) {

                    Row(

                        modifier =
                            Modifier.padding(14.dp),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.CheckCircle,

                            contentDescription =
                                null,

                            tint =
                                Color(0xFF4CAF50),

                            modifier =
                                Modifier.size(24.dp)

                        )

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Column {

                            Text(

                                text =
                                    "Safety",

                                color =
                                    Color(0xFF8FA4B8),

                                style =
                                    MaterialTheme.typography.bodySmall

                            )

                            Text(

                                text =
                                    safetyStatus,

                                color =
                                    if (
                                        safetyStatus.equals(
                                            "SAFE",
                                            ignoreCase = true
                                        )
                                    )
                                        Color(0xFF4CAF50)
                                    else
                                        Color(0xFFFFB300),

                                fontWeight =
                                    FontWeight.Bold

                            )

                        }

                    }

                }


                /*
                 * CURRENT LOCATION
                 */

                Card(

                    modifier =
                        Modifier.weight(1f),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                Color(0xFF0D1B2A)

                        )

                ) {

                    Row(

                        modifier =
                            Modifier.padding(14.dp),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.LocationOn,

                            contentDescription =
                                null,

                            tint =
                                Color(0xFF42A5F5),

                            modifier =
                                Modifier.size(24.dp)

                        )

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Column {

                            Text(

                                text =
                                    "Current Location",

                                color =
                                    Color(0xFF8FA4B8),

                                style =
                                    MaterialTheme.typography.bodySmall

                            )

                            Text(

                                text =
                                    currentLocation,

                                color =
                                    Color.White,

                                fontWeight =
                                    FontWeight.Bold,

                                maxLines =
                                    2

                            )

                        }

                    }

                }

            }


            /*
             * ====================================================
             * LAST UPDATED
             * ====================================================
             */

            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Icon(

                    imageVector =
                        Icons.Default.Schedule,

                    contentDescription =
                        null,

                    tint =
                        Color(0xFF8FA4B8),

                    modifier =
                        Modifier.size(20.dp)

                )

                Spacer(
                    modifier =
                        Modifier.width(8.dp)
                )

                Text(

                    text =
                        "Last updated: $lastUpdated",

                    color =
                        Color(0xFF8FA4B8),

                    style =
                        MaterialTheme.typography.bodyMedium

                )

            }

        }

    }

}