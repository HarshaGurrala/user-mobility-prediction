package com.usermobilityprediction.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AIPulse(modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.primary, radius: Dp = 24.dp) {
    val transition = rememberInfiniteTransition()
    val scale by transition.animateFloat(initialValue = 0.8f, targetValue = 1.6f, animationSpec = infiniteRepeatable(tween(900, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Restart))
    val alpha by transition.animateFloat(initialValue = 0.4f, targetValue = 0f, animationSpec = infiniteRepeatable(tween(900), repeatMode = RepeatMode.Restart))

    Box(modifier = modifier.size(radius * 2), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(color = color.copy(alpha = alpha), radius = (size.minDimension / 2) * scale)
        }
        Box(modifier = Modifier.size(radius).clip(CircleShape).background(color))
    }
}

@Composable
fun AnimatedStatusIndicator(status: String, modifier: Modifier = Modifier) {
    val color = when (status.lowercase()) {
        "safe" -> Color(0xFF4CAF50)
        "warning" -> Color(0xFFFFC107)
        "danger" -> Color(0xFFF44336)
        else -> MaterialTheme.colorScheme.primary
    }
    val infinite = rememberInfiniteTransition()
    val scale by infinite.animateFloat(initialValue = 0.9f, targetValue = 1.12f, animationSpec = infiniteRepeatable(tween(900), repeatMode = RepeatMode.Reverse))
    Box(modifier = modifier.size(14.dp).scale(scale).clip(RoundedCornerShape(8.dp)).background(color))
}

@Composable
fun PressableGlassCard(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null, content: @Composable () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.985f else 1f)
    Box(modifier = modifier
        .scale(scale)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White.copy(alpha = 0.02f))) {
        Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            content()
        }
    }
}

@Composable
fun <T> SimplePullToRefresh(state: MutableState<Boolean>, modifier: Modifier = Modifier, onRefresh: suspend () -> Unit, content: @Composable (Modifier) -> Unit) {
    val scope = rememberCoroutineScope()
    var offsetY by remember { mutableStateOf(0f) }
    val pullThreshold = 120f

    Box(modifier = modifier.pointerInput(Unit) {
        detectVerticalDragGestures(onVerticalDrag = { change, dragAmount ->
            offsetY = (offsetY + dragAmount).coerceAtLeast(0f)
        }, onDragEnd = {
            if (offsetY > pullThreshold) {
                scope.launch {
                    state.value = true
                    onRefresh()
                    state.value = false
                }
            }
            offsetY = 0f
        }, onDragCancel = { offsetY = 0f })
    }) {
        Column {
            if (state.value) {
                Box(modifier = Modifier.fillMaxWidth().height(56.dp), contentAlignment = Alignment.Center) {
                    Text("Refreshing...", style = MaterialTheme.typography.bodySmall)
                }
            } else if (offsetY > 0f) {
                Box(modifier = Modifier.fillMaxWidth().height((offsetY / 3).dp), contentAlignment = Alignment.Center) {
                    Text("Pull to refresh", style = MaterialTheme.typography.bodySmall)
                }
            }
            content(Modifier)
        }
    }
}

@Composable
fun SuccessBadge(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val scale by transition.animateFloat(initialValue = 0.95f, targetValue = 1.06f, animationSpec = infiniteRepeatable(tween(700), repeatMode = RepeatMode.Reverse))
    Box(modifier = modifier.scale(scale).size(40.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFF4CAF50)), contentAlignment = Alignment.Center) {
        Text("✓", color = Color.White)
    }
}

@Composable
fun ErrorBadge(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val scale by transition.animateFloat(initialValue = 0.95f, targetValue = 1.06f, animationSpec = infiniteRepeatable(tween(700), repeatMode = RepeatMode.Reverse))
    Box(modifier = modifier.scale(scale).size(40.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFF44336)), contentAlignment = Alignment.Center) {
        Text("!", color = Color.White)
    }
}
