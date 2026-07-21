package com.usermobilityprediction.app.ui.components.landing

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CenterGlobe() {

    val infinite = rememberInfiniteTransition(label = "")

    val rotation by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(45000, easing = LinearEasing)
        ),
        label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(500.dp)
    ) {

        Box(
            modifier = Modifier
                .size(250.dp)
                .rotate(rotation)
                .background(
                    Brush.radialGradient(
                        listOf(
                            Color(0x223B82F6),
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(170.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF111827),
                            Color(0xFF1F2937),
                            Color(0xFF111827)
                        )
                    ),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                "🌍",
                color = Color.White,
                fontSize = 100.sp
            )

        }

    }

}