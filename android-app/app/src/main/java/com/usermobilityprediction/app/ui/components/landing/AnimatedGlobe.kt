package com.usermobilityprediction.app.ui.components.landing

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedGlobe() {

    val transition = rememberInfiniteTransition(label = "globe")

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val orbit by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing)
        ),
        label = "orbit"
    )

    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.size(300.dp)) {

            // Blue Glow
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        Color(0x443B82F6),
                        Color.Transparent
                    )
                ),
                radius = size.minDimension / 2
            )

            // Globe
            drawCircle(
                color = Color(0xFF111827),
                radius = 110.dp.toPx()
            )

            rotate(rotation) {

                // Latitude
                for (i in 1..7) {

                    drawCircle(
                        color = Color(0x22FFFFFF),
                        radius = (110 - i * 12).dp.toPx(),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(1.dp.toPx())
                    )
                }

                // Longitude
                for (i in 0 until 12) {

                    val angle = i * 30f

                    rotate(angle) {
                        drawLine(
                            color = Color(0x33FFFFFF),
                            start = Offset(size.width / 2, size.height / 2 - 110.dp.toPx()),
                            end = Offset(size.width / 2, size.height / 2 + 110.dp.toPx())
                        )
                    }
                }
            }

            // Orbit Ring
            drawCircle(
                color = Color(0x22FFFFFF),
                radius = 135.dp.toPx(),
                style = androidx.compose.ui.graphics.drawscope.Stroke(1.dp.toPx())
            )

            rotate(orbit) {

                drawCircle(
                    color = Color(0xFF22D3EE),
                    radius = 7.dp.toPx(),
                    center = Offset(
                        size.width / 2,
                        size.height / 2 - 135.dp.toPx()
                    )
                )
            }
        }
    }
}