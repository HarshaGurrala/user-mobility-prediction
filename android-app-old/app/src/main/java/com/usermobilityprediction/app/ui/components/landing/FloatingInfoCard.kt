package com.usermobilityprediction.app.ui.components.landing

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FloatingInfoCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    delay: Int = 0
) {

    val infinite = rememberInfiniteTransition(label = "")

    val y by infinite.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                4000,
                delayMillis = delay
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Column(
        modifier = Modifier
            .offset(y = y.dp)
            .background(
                Color.White.copy(alpha = .05f),
                RoundedCornerShape(26.dp)
            )
            .padding(18.dp)
            .width(170.dp)
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor
        )

        Spacer(Modifier.height(12.dp))

        Text(
            title,
            color = Color.White,
            fontSize = 17.sp
        )

        Spacer(Modifier.height(6.dp))

        Text(
            subtitle,
            color = Color.Gray,
            fontSize = 13.sp
        )

    }

}