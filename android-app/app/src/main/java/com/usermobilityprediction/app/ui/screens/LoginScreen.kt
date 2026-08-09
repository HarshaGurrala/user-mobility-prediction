package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.navigation.Routes
import com.usermobilityprediction.app.ui.components.GlassTextField
import com.usermobilityprediction.app.ui.components.PasswordTextField
import com.usermobilityprediction.app.ui.components.PrimaryButton
import com.usermobilityprediction.app.viewmodel.LoginViewModel
import androidx.compose.material3.TextButton



@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var validationError by remember {
        mutableStateOf<String?>(null)
    }

    val loading by
    loginViewModel.loading.collectAsState()

    val success by
    loginViewModel.success.collectAsState()



    val apiError by
    loginViewModel.error.collectAsState()

    val userRole by loginViewModel.userRole.collectAsState()


    LaunchedEffect(success, userRole) {

        if(success){

            if(userRole == "GUARDIAN"){


                navController.navigate(
                    "guardian_connect"
                ){

                    popUpTo(
                        Routes.LANDING
                    ){

                        inclusive = true
                    }

                }


            } else {


                navController.navigate(
                    Routes.APP_SHELL
                ){

                    popUpTo(
                        Routes.LANDING
                    ){

                        inclusive = true
                    }

                }

            }

        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF050505)
            )
    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier =
                    Modifier.height(80.dp)
            )

            Text(
                text = "Welcome Back",

                style =
                    MaterialTheme.typography
                        .headlineMedium,

                color =
                    Color.White
            )

            Text(
                text =
                    "Sign in to SafePath AI",

                color =
                    Color.Gray
            )

            Spacer(
                modifier =
                    Modifier.height(40.dp)
            )

            Card(

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White.copy(
                                alpha = 0.05f
                            )
                    )
            ) {

                Column(

                    modifier =
                        Modifier.padding(
                            20.dp
                        )
                ) {

                    GlassTextField(

                        value =
                            email,

                        onValueChange = {
                            email = it
                        },

                        label =
                            "Email"
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                16.dp
                            )
                    )

                    PasswordTextField(

                        value =
                            password,

                        onValueChange = {
                            password = it
                        },

                        label =
                            "Password"
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                16.dp
                            )
                    )

                    validationError?.let {

                        Text(
                            text = it,

                            color =
                                Color.Red
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    8.dp
                                )
                        )
                    }

                    apiError?.let {

                        Text(
                            text = it,

                            color =
                                Color.Red
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    8.dp
                                )
                        )
                    }




                    PrimaryButton(

                        text =
                            if (loading)
                                "Signing In..."
                            else
                                "Login",

                        onClick = {

                            validationError =
                                null

                            when {

                                email.isBlank() -> {

                                    validationError =
                                        "Please enter your email"
                                }

                                !android.util.Patterns
                                    .EMAIL_ADDRESS
                                    .matcher(
                                        email
                                    )
                                    .matches() -> {

                                    validationError =
                                        "Please enter a valid email"
                                }

                                password.isBlank() -> {

                                    validationError =
                                        "Please enter your password"
                                }

                                !loading -> {

                                    loginViewModel.login(

                                        email =
                                            email,

                                        password =
                                            password
                                    )
                                }
                            }
                        }
                    )

                    TextButton(
                        onClick = {
                            navController.navigate("forgot-password")
                        }
                    ) {
                        Text(
                            text = "Forgot Password?",
                            color = Color(0xFF3B82F6)
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(
                                20.dp
                            )
                    )

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.Center
                    ) {

                        Text(

                            text =
                                "Don't have an account? ",

                            color =
                                Color.Gray
                        )

                        Text(

                            text =
                                "Register",

                            color =
                                Color(0xFF3B82F6),

                            modifier =
                                Modifier.clickable {

                                    navController.navigate(
                                        Routes.REGISTER
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}