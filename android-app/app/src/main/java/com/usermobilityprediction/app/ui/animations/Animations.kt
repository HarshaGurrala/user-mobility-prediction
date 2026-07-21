package com.usermobilityprediction.app.ui.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Simple shimmer brush generator
@Composable
fun rememberShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition()
    val x by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(animation = tween(1200, easing = LinearEasing))
    )
    return Brush.linearGradient(
        colors = listOf(Color.LightGray.copy(alpha = 0.2f), Color.LightGray.copy(alpha = 0.05f), Color.LightGray.copy(alpha = 0.2f)),
        start = Offset(x - 200f, 0f),
        end = Offset(x, 0f)
    )
}

// Collect pressed state from InteractionSource
@Composable
fun MutableInteractionSource.collectIsPressedAsState(): State<Boolean> {
    val pressed = remember { mutableStateOf(false) }
    LaunchedEffect(this) {
        interactions.collect { interaction: Interaction ->
            when (interaction) {
                is PressInteraction.Press -> pressed.value = true
                is PressInteraction.Release -> pressed.value = false
                is PressInteraction.Cancel -> pressed.value = false
            }
        }
    }
    return pressed
}

// Small helper for converting offset px to IntOffset
