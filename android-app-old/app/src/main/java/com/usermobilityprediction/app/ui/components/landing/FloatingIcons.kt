package com.usermobilityprediction.app.ui.components.landing

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FloatingIcons() {

    FloatingIcon(
        x = (-130).dp,
        y = (-170).dp,
        rotation = 10f,
        icon = {
            Icon(
                Icons.Default.Public,
                null,
                tint = Color(0xFF60A5FA)
            )
        }
    )

    FloatingIcon(
        x = 140.dp,
        y = (-80).dp,
        rotation = -8f,
        icon = {
            Icon(
                Icons.Default.MyLocation,
                null,
                tint = Color(0xFF22D3EE)
            )
        }
    )

    FloatingIcon(
        x = (-150).dp,
        y = 120.dp,
        rotation = 8f,
        icon = {
            Icon(
                Icons.Default.LocationOn,
                null,
                tint = Color(0xFF3B82F6)
            )
        }
    )

    FloatingIcon(
        x = 130.dp,
        y = 150.dp,
        rotation = -12f,
        icon = {
            Icon(
                Icons.Default.Security,
                null,
                tint = Color(0xFF8B5CF6)
            )
        }
    )

    FloatingIcon(
        x = 0.dp,
        y = 210.dp,
        rotation = 5f,
        icon = {
            Icon(
                Icons.Default.Timeline,
                null,
                tint = Color(0xFF60A5FA)
            )
        }
    )
}

@Composable
private fun FloatingIcon(
    x: androidx.compose.ui.unit.Dp,
    y: androidx.compose.ui.unit.Dp,
    rotation: Float,
    icon: @Composable () -> Unit
) {

    val transition = rememberInfiniteTransition(label = "")

    val offsetY by transition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .offset(x, y + offsetY.dp)
            .rotate(rotation)
            .shadow(
                30.dp,
                RoundedCornerShape(28.dp),
                ambientColor = Color(0x663B82F6)
            )
            .background(
                Color.White.copy(alpha = 0.05f),
                RoundedCornerShape(28.dp)
            )
            .size(72.dp)
    ) {
        Box(
            modifier = Modifier.matchParentSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            icon()
        }
    }
}