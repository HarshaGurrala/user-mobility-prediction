package com.usermobilityprediction.app.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight


@Composable
fun AIMobilityOverviewCard() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0x33219BFF),
                        Color(0x1100A8FF)
                    )
                ),
                shape = RoundedCornerShape(32.dp)
            )
            .padding(24.dp)
    ) {


        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Outlined.AutoGraph,
                    contentDescription = null,
                    tint = Color(0xFF4DB8FF),
                    modifier = Modifier.size(32.dp)
                )


                Spacer(
                    modifier = Modifier.width(12.dp)
                )


                Text(
                    text = "AI Mobility Intelligence",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(22.dp)
            )


            Text(
                text = "Next Prediction",
                color = Color.Gray,
                fontSize = 14.sp
            )


            Spacer(
                modifier = Modifier.height(6.dp)
            )


            Text(
                text = "Analyzing movement pattern...",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(16.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Column {

                    Text(
                        text = "Confidence",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "94%",
                        color = Color(0xFF4DB8FF),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                Column {

                    Text(
                        text = "Status",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "Protected",
                        color = Color(0xFF4CFF88),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}