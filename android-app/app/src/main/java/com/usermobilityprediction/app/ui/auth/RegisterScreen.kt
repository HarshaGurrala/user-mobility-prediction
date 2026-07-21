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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.viewmodel.AuthState
import com.usermobilityprediction.app.viewmodel.AuthViewModel
import com.usermobilityprediction.app.utils.Validation
import com.usermobilityprediction.app.ui.components.common.GlassTextField


@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    val state by authViewModel.state.collectAsState()

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("USER") }
    var validationError by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(state) {

        when (state) {

            is AuthState.Success -> {

                val msg = (state as AuthState.Success).message

                if (msg == "registered" || msg == "logged_in") {

                    navController.navigate("app") {
                        popUpTo("register") {
                            inclusive = true
                        }
                    }
                }
            }

            else -> {}
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505)),
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
                text = "Create Account",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(
                text = "Join AI powered mobility protection",
                color = Color.Gray,
                fontSize = 14.sp
            )


            Spacer(
                modifier = Modifier.height(28.dp)
            )


            Text(
                text = "Account Type",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = selectedRole == "USER",
                        onClick = {
                            selectedRole = "USER"
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF4DB8FF)
                        )
                    )

                    Text(
                        text = "User",
                        color = Color.White
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = selectedRole == "GUARDIAN",
                        onClick = {
                            selectedRole = "GUARDIAN"
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF4DB8FF)
                        )
                    )

                    Text(
                        text = "Guardian",
                        color = Color.White
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(14.dp)
            )

            authViewModel.register(
                fullName.trim(),
                email.trim(),
                phone.trim().ifBlank { null },
                password,
                selectedRole
            )


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            GlassTextField(
                value = phone,
                onValueChange = {
                    phone = it
                },
                placeholder = "Phone Number"
            )


            Spacer(
                modifier = Modifier.height(14.dp)
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
                modifier = Modifier.height(16.dp)
            )


            val isFormValid =
                Validation.isFullNameValid(fullName) &&
                        Validation.isEmailValid(email) &&
                        Validation.isPasswordValid(password) &&
                        Validation.isPhoneValid(phone)



            Button(
                onClick = {

                    if (isFormValid) {

                        validationError = null

                        authViewModel.register(
                            fullName.trim(),
                            email.trim(),
                            phone.trim().ifBlank { null },
                            password,
                            "USER"
                        )

                    } else {

                        validationError =
                            "Please fill all fields correctly"
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
                    text = "Create Account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            TextButton(
                onClick = {
                    navController.navigate("login")
                }
            ) {

                Text(
                    text = "Already have an account? Sign in",
                    color = Color(0xFF4DB8FF)
                )
            }
        }
    }
}