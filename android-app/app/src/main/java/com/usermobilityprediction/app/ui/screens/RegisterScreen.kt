package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.navigation.Routes
import com.usermobilityprediction.app.ui.components.GlassTextField
import com.usermobilityprediction.app.ui.components.PasswordTextField
import com.usermobilityprediction.app.ui.components.PrimaryButton
import com.usermobilityprediction.app.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel()
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var role by remember { mutableStateOf("User") }

    var validationError by remember {
        mutableStateOf<String?>(null)
    }

    val loading by registerViewModel.loading.collectAsState()
    val success by registerViewModel.success.collectAsState()
    val apiError by registerViewModel.error.collectAsState()

    /*
     * Registration success
     */
    LaunchedEffect(success) {

        if (success) {

            navController.navigate(Routes.LOGIN) {

                popUpTo(Routes.REGISTER) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(60.dp)
            )

            Text(
                text = "SafePath AI",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Text(
                text = "Create your account",
                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.05f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    GlassTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = "Full Name"
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    GlassTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = "Email"
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    GlassTextField(
                        value = phoneNumber,
                        onValueChange = {
                            phoneNumber = it
                        },
                        label = "Phone Number"
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    PasswordTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = "Password"
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    PasswordTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                        },
                        label = "Confirm Password"
                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    Text(
                        text = "Select Role",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Row {

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .selectable(
                                    selected = role == "User",
                                    onClick = {
                                        role = "User"
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            RadioButton(
                                selected = role == "User",
                                onClick = {
                                    role = "User"
                                }
                            )

                            Text(
                                text = "User",
                                color = Color.White
                            )
                        }

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .selectable(
                                    selected = role == "Guardian",
                                    onClick = {
                                        role = "Guardian"
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            RadioButton(
                                selected = role == "Guardian",
                                onClick = {
                                    role = "Guardian"
                                }
                            )

                            Text(
                                text = "Guardian",
                                color = Color.White
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    validationError?.let {

                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier.padding(
                                bottom = 12.dp
                            )
                        )
                    }

                    apiError?.let {

                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier.padding(
                                bottom = 12.dp
                            )
                        )
                    }

                    PrimaryButton(
                        text = if (loading) {
                            "Creating Account..."
                        } else {
                            "Create Account"
                        },
                        onClick = {

                            validationError = null

                            when {

                                name.isBlank() ->
                                    validationError =
                                        "Please enter your full name"

                                email.isBlank() ->
                                    validationError =
                                        "Please enter your email"

                                !android.util.Patterns.EMAIL_ADDRESS
                                    .matcher(email)
                                    .matches() ->
                                    validationError =
                                        "Please enter a valid email"

                                phoneNumber.isBlank() ->
                                    validationError =
                                        "Please enter your phone number"

                                password.isBlank() ->
                                    validationError =
                                        "Please enter your password"

                                password.length < 6 ->
                                    validationError =
                                        "Password must contain at least 6 characters"

                                password != confirmPassword ->
                                    validationError =
                                        "Passwords do not match"

                                !loading -> {

                                    registerViewModel.register(

                                        fullName = name.trim(),

                                        email = email.trim(),

                                        phoneNumber =
                                            phoneNumber.trim(),

                                        password = password,

                                        role = role
                                    )
                                }
                            }
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Row(
                        horizontalArrangement =
                            Arrangement.Center,

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "Already have an account? ",
                            color = Color.Gray
                        )

                        Text(
                            text = "Login",
                            color = Color(0xFF3B82F6),

                            modifier = Modifier.clickable {

                                navController.navigate(
                                    Routes.LOGIN
                                )
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}