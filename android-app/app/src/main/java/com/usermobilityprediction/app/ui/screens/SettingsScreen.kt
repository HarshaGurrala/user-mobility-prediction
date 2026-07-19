package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val notifications = remember { mutableStateOf(true) }
        Text("Notifications")
        Switch(checked = notifications.value, onCheckedChange = { notifications.value = it })

        TextButton(onClick = {}) { Text("Privacy Policy") }
        TextButton(onClick = {}) { Text("Terms & Conditions") }
    }
}
