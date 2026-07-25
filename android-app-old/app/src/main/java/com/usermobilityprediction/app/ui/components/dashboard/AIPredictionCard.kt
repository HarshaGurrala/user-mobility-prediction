package com.usermobilityprediction.app.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AIPredictionCard(
    destination: String?,
    confidence: Double?,
    eta: String?
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0x33219BFF),
                        Color(0x11111111)
                    )
                ),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(22.dp)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                imageVector = Icons.Outlined.AutoGraph,
                contentDescription = null,
                tint = Color(0xFF4DB8FF),
                modifier = Modifier.size(34.dp)
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            Text(
                text = "AI Mobility Prediction",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }



        Spacer(
            modifier = Modifier.height(20.dp)
        )


        Text(
            text = "Next Destination",
            color = Color.Gray,
            fontSize = 13.sp
        )


        Spacer(
            modifier = Modifier.height(6.dp)
        )


        Text(
            text = destination ?: "Waiting for AI prediction...",
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
                    text =
                        confidence?.let {
                            "${it}%"
                        } ?: "--",
                    color = Color(0xFF4CFF88),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }



            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = "ETA",
                    color = Color.Gray,
                    fontSize = 12.sp
                )


                Text(
                    text = eta ?: "--",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}