package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.ui.components.PrimaryGradientButton

@Composable
fun EmergencyScreen() {
    val showDialog = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Emergency / SOS", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        Text("Trigger an emergency alert to your guardians and emergency contacts.")
        Spacer(Modifier.height(16.dp))
        PrimaryGradientButton(text = "Send SOS", onClick = { showDialog.value = true })
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = { TextButton(onClick = { showDialog.value = false }) { Text("OK") } },
            title = { Text("Mock SOS sent") },
            text = { Text("This is a mock emergency alert for demo purposes.") }
        )
    }
}
