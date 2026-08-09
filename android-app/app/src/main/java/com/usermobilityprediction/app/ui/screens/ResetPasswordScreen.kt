package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.viewmodel.ResetPasswordViewModel
import com.usermobilityprediction.app.navigation.Routes

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    token: String,
    viewModel: ResetPasswordViewModel = viewModel()
) {

    var newPassword by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    val loading by viewModel.loading.collectAsState()
    val message by viewModel.message.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(message) {

        if (message != null) {

            navController.navigate(
                Routes.LOGIN
            ) {

                popUpTo(0) {
                    inclusive = true
                }

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Reset Password",
            color = Color.White
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("New Password")
            },
            visualTransformation =
                PasswordVisualTransformation()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Confirm Password")
            },
            visualTransformation =
                PasswordVisualTransformation()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (error != null) {

            Text(
                text = error ?: "",
                color = Color.Red
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading,
            onClick = {

                if (
                    newPassword.isNotBlank() &&
                    newPassword == confirmPassword
                ) {

                    viewModel.resetPassword(
                        token = token,
                        newPassword = newPassword
                    )

                }

            }
        ) {

            if (loading) {

                CircularProgressIndicator()

            } else {

                Text(
                    text = "Reset Password"
                )

            }
        }
    }
}