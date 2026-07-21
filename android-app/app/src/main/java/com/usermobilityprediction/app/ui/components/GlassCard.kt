package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.ui.animations.rememberShimmerBrush

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    shimmer: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {

    val shimmerBrush: Brush? =
        if (shimmer) rememberShimmerBrush() else null

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp)),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(
            1.dp,
            Color.White.copy(alpha = 0.08f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Box(
            modifier = Modifier
                .then(
                    if (shimmerBrush != null)
                        Modifier.background(shimmerBrush)
                    else
                        Modifier
                )
                .padding(contentPadding)
        ) {
            content(contentPadding)
        }

    }
}