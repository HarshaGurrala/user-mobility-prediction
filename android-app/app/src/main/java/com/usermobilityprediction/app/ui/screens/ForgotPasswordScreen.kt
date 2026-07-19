package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.ui.components.AppTextField
import com.usermobilityprediction.app.ui.components.PrimaryGradientButton
import com.usermobilityprediction.app.utils.Validation
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun ForgotPasswordScreen() {
    var email by remember { mutableStateOf("") }
    var validationError by remember { mutableStateOf<String?>(null) }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Forgot password", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        AppTextField(value = email, onValueChange = { email = it }, label = "Email")
        Spacer(Modifier.height(12.dp))
        val valid = Validation.isEmailValid(email)
        PrimaryGradientButton(text = "Send reset link", onClick = { if (valid) { validationError = null } else validationError = "Enter a valid email" }, modifier = Modifier.fillMaxWidth())
        validationError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
