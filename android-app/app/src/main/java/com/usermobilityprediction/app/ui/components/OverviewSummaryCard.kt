package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OverviewSummaryCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    description: String,
    icon: ImageVector
) {

    val iconBackground =
        Color(0xFF1688FF).copy(alpha = 0.14f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF101820),
                shape = RoundedCornerShape(22.dp)
            )
            .padding(16.dp),

        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween,
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement =
                    Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = title,
                    style =
                        MaterialTheme.typography.bodyMedium,
                    color =
                        Color(0xFF91A4B8),
                    fontWeight =
                        FontWeight.Medium
                )

                Text(
                    text = value,
                    style =
                        MaterialTheme.typography.titleLarge,
                    color =
                        Color.White,
                    fontWeight =
                        FontWeight.Bold
                )

                Text(
                    text = description,
                    style =
                        MaterialTheme.typography.labelSmall,
                    color =
                        Color(0xFF64788C)
                )
            }

            Column(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = iconBackground,
                        shape = RoundedCornerShape(14.dp)
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF3FA9FF),
                    modifier = Modifier.size(23.dp)
                )
            }
        }
    }
}