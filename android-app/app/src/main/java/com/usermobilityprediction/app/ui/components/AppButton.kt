package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.ui.theme.AccentViolet
import com.usermobilityprediction.app.ui.theme.PrimaryBlue
import com.usermobilityprediction.app.ui.theme.PrimaryBlueDark

@Composable
fun PrimaryGradientButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val gradient = Brush.horizontalGradient(listOf(PrimaryBlue, AccentViolet))

    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.background(brush = gradient), contentAlignment = Alignment.Center) {
            Text(text = text, modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}
