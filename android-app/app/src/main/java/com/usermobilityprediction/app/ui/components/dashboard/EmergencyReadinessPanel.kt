package com.usermobilityprediction.app.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
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
fun EmergencyReadinessPanel() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0x44FF3333),
                        Color(0x22111111)
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = null,
            tint = Color(0xFFFF5252),
            modifier = Modifier.size(36.dp)
        )


        Spacer(
            modifier = Modifier.width(14.dp)
        )


        Column {

            Text(
                text = "Emergency Ready",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )


            Text(
                text = "One tap protection available",
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}