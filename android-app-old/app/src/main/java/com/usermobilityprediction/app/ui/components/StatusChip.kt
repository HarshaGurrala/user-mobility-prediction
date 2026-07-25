package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.ui.theme.SuccessGreen
import com.usermobilityprediction.app.ui.theme.WarningOrange
import com.usermobilityprediction.app.ui.theme.DangerRed
import com.usermobilityprediction.app.ui.theme.InfoBlue

@Composable
fun StatusChip(status: String, modifier: Modifier = Modifier) {
    val color = when (status.lowercase()) {
        "safe" -> SuccessGreen
        "warning" -> WarningOrange
        "danger" -> DangerRed
        else -> InfoBlue
    }

    Text(
        text = status.uppercase(),
        modifier = modifier
            .background(color.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = color
    )
}
