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
fun AIPredictionDetailsCard(
    destination: String = "Analyzing destination",
    confidence: Int = 94,
    eta: String = "Calculating..."
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
                shape = RoundedCornerShape(32.dp)
            )
            .padding(24.dp)
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


            Column {

                Text(
                    text = "AI Prediction Engine",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    text = "Real-time mobility intelligence",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }


        Spacer(
            modifier = Modifier.height(22.dp)
        )


        Text(
            text = "Next Destination",
            color = Color.Gray,
            fontSize = 13.sp
        )


        Text(
            text = destination,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(18.dp)
        )


        Text(
            text = "Prediction Confidence",
            color = Color.Gray,
            fontSize = 13.sp
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        LinearProgressIndicator(
            progress = confidence / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = Color(0xFF168CFF),
            trackColor = Color(0x22FFFFFF)
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        Text(
            text = "$confidence% Accuracy",
            color = Color(0xFF4DB8FF),
            fontSize = 16.sp,
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
                    text = "Estimated Arrival",
                    color = Color.Gray,
                    fontSize = 12.sp
                )


                Text(
                    text = eta,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Text(
                text = "AI Active",
                color = Color(0xFF4CFF88),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}