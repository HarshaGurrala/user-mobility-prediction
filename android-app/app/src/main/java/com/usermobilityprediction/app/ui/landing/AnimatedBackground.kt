package com.usermobilityprediction.app.ui.landing

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedBackground() {

    val infinite = rememberInfiniteTransition(label = "")

    val glow by infinite.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            tween(6000),
            RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        Color(0x443B82F6),
                        Color.Transparent
                    )
                ),
                radius = size.width * .55f * glow,
                center = Offset(size.width / 2, -120f)
            )

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        Color(0x228B5CF6),
                        Color.Transparent
                    )
                ),
                radius = size.width * .35f,
                center = Offset(-80f, size.height / 2)
            )

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        Color(0x2260A5FA),
                        Color.Transparent
                    )
                ),
                radius = size.width * .32f,
                center = Offset(size.width + 80f, size.height * .8f)
            )

        }

    }

}