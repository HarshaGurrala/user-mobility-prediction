package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.ui.components.GlassCard

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        GlassCard {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Harsha Gurrala", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Text("harsha@example.com", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
            }
        }

        GlassCard {
            Text("Guardian: John Doe\nPhone: +1 555 1234")
        }
    }
}
