package com.usermobilityprediction.app.ui.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight


@Composable
fun LandingHeader() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(52.dp)
                .background(
                    Color(0x2200A8FF),
                    RoundedCornerShape(18.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Outlined.AutoGraph,
                contentDescription = null,
                tint = Color(0xFF4DB8FF),
                modifier = Modifier.size(28.dp)
            )
        }


        Spacer(
            modifier = Modifier.width(14.dp)
        )


        Column {

            Text(
                text = "User Mobility",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Prediction",
                color = Color(0xFF4DB8FF),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(
            modifier = Modifier.weight(1f)
        )


        Box(
            modifier = Modifier
                .background(
                    Color(0x2200A8FF),
                    RoundedCornerShape(50.dp)
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = 8.dp
                )
        ) {

            Text(
                text = "AI POWERED",
                color = Color(0xFF4DB8FF),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}