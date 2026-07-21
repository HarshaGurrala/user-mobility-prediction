package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.ui.components.PressableGlassCard

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val notifications = remember { mutableStateOf(true) }
        Text("Notifications")
        Switch(checked = notifications.value, onCheckedChange = { notifications.value = it })
        Spacer(Modifier.height(12.dp))
        val dialogTitle = remember { mutableStateOf("") }
        val dialogText = remember { mutableStateOf("") }
        val showDialog = remember { mutableStateOf(false) }

        PressableGlassCard(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { dialogTitle.value = "Privacy Policy"; dialogText.value = "Privacy policy content (mock)."; showDialog.value = true }) { Text("Privacy Policy") }
        }
        Spacer(Modifier.height(8.dp))
        PressableGlassCard(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { dialogTitle.value = "Terms & Conditions"; dialogText.value = "Terms and conditions (mock)."; showDialog.value = true }) { Text("Terms & Conditions") }
        }

        if (showDialog.value) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(dialogTitle.value) },
                text = { Text(dialogText.value) },
                confirmButton = { TextButton(onClick = { showDialog.value = false }) { Text("OK") } }
            )
        }
    }
}
