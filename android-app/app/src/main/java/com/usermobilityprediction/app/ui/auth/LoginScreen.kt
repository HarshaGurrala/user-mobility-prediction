package com.usermobilityprediction.app.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.viewmodel.AuthState
import com.usermobilityprediction.app.viewmodel.AuthViewModel
import com.usermobilityprediction.app.utils.Validation

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val state by authViewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var validationError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state) {
        when (state) {
            is AuthState.Success -> navController.navigate("app") {
                popUpTo("login") { inclusive = true }
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        when (state) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Error -> Text((state as AuthState.Error).error, color = MaterialTheme.colorScheme.error)
            else -> {}
        }
        Spacer(Modifier.height(8.dp))
        val isFormValid = Validation.isEmailValid(email) && Validation.isPasswordValid(password)
        Button(onClick = { if (isFormValid) { validationError = null; authViewModel.login(email.trim(), password) } else validationError = "Please enter a valid email and password" }, modifier = Modifier.fillMaxWidth(), enabled = isFormValid.not().not()) {
            Text("Sign In")
        }
        validationError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = { navController.navigate("register") }) {
            Text("Create account")
        }
    }
}
