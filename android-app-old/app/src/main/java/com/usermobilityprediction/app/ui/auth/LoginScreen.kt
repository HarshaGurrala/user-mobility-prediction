package com.usermobilityprediction.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.viewmodel.AuthState
import com.usermobilityprediction.app.viewmodel.AuthViewModel
import com.usermobilityprediction.app.utils.Validation
import com.usermobilityprediction.app.ui.components.common.GlassTextField


@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    val state by authViewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var validationError by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(state) {

        when (state) {

            is AuthState.Success -> {

                val msg = (state as AuthState.Success).message

                if (msg == "logged_in" || msg == "registered") {

                    navController.navigate("app") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            }

            else -> {}
        }
    }


    val focusManager = LocalFocusManager.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF050505)
            ),
        contentAlignment = Alignment.Center
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0x33FFFFFF),
                            Color(0x1100A8FF)
                        )
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = "Welcome Back",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(
                text = "Login to continue your AI mobility journey",
                color = Color.Gray,
                fontSize = 14.sp
            )


            Spacer(
                modifier = Modifier.height(32.dp)
            )


            GlassTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                placeholder = "Email"
            )


            Spacer(
                modifier = Modifier.height(16.dp)
            )


            GlassTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                placeholder = "Password",
                visualTransformation = PasswordVisualTransformation()
            )


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            when (state) {

                is AuthState.Loading -> {
                    CircularProgressIndicator(
                        color = Color(0xFF4DB8FF)
                    )
                }


                is AuthState.Error -> {

                    Text(
                        text = (state as AuthState.Error).error,
                        color = Color.Red,
                        fontSize = 13.sp
                    )
                }


                else -> {}
            }


            validationError?.let {

                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            val isFormValid =
                Validation.isEmailValid(email) &&
                        Validation.isPasswordValid(password)



            Button(
                onClick = {

                    if (isFormValid) {

                        validationError = null
                        authViewModel.login(
                            email.trim(),
                            password
                        )

                    } else {

                        validationError =
                            "Please enter a valid email and password"
                    }
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF168CFF)
                )
            ) {

                Text(
                    text = "Sign In",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                TextButton(
                    onClick = {
                        navController.navigate("forgot")
                    }
                ) {

                    Text(
                        text = "Forgot password?",
                        color = Color(0xFF4DB8FF)
                    )
                }


                TextButton(
                    onClick = {
                        navController.navigate("register")
                    }
                ) {

                    Text(
                        text = "Create account",
                        color = Color(0xFF4DB8FF)
                    )
                }
            }
        }
    }
}