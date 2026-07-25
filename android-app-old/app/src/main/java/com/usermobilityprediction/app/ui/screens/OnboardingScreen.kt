package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.ui.components.PrimaryGradientButton

@Composable
fun OnboardingScreen(onFinish: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Welcome to User Mobility Prediction", style = androidx.compose.material3.MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(8.dp))
        Text("Get predictive insights and stay safe on the move.")
        Spacer(Modifier.height(24.dp))
        PrimaryGradientButton(text = "Get Started", onClick = onFinish)
    }
}
