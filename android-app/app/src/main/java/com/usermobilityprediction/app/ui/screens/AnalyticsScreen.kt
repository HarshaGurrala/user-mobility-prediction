package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight


@Composable
fun AnalyticsScreen() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {


        Text(
            text = "Mobility Analytics",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )



        AnalyticsGlassCard(
            icon = Icons.Outlined.Analytics,
            title = "AI Prediction Accuracy",
            value = "Waiting for data"
        )



        AnalyticsGlassCard(
            icon = Icons.Outlined.Route,
            title = "Movement History",
            value = "No trips analysed"
        )



        AnalyticsGlassCard(
            icon = Icons.Outlined.AutoGraph,
            title = "Mobility Pattern",
            value = "Learning behaviour"
        )
    }
}



@Composable
private fun AnalyticsGlassCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {


    Row(
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
            .padding(22.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4DB8FF),
            modifier = Modifier.size(36.dp)
        )


        Spacer(
            modifier = Modifier.width(16.dp)
        )


        Column {


            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )


            Text(
                text = value,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}