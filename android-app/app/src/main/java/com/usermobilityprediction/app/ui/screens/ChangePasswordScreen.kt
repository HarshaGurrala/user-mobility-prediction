package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController

import com.usermobilityprediction.app.ui.components.GlassTextField
import com.usermobilityprediction.app.ui.components.PrimaryButton

import com.usermobilityprediction.app.viewmodel.ChangePasswordViewModel


@Composable
fun ChangePasswordScreen(

    navController: NavController,

    viewModel: ChangePasswordViewModel =
        viewModel()

) {


    var currentPassword by remember {

        mutableStateOf("")
    }


    var newPassword by remember {

        mutableStateOf("")
    }


    var confirmPassword by remember {

        mutableStateOf("")
    }


    var validationError by remember {

        mutableStateOf<String?>(null)
    }


    val loading by
    viewModel.loading.collectAsState()


    val success by
    viewModel.success.collectAsState()


    val error by
    viewModel.error.collectAsState()


    LaunchedEffect(success) {


        if (success) {


            navController.popBackStack()


            viewModel.resetState()
        }
    }


    Box(

        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Color(0xFF050505)
                )
    ) {


        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {


            Spacer(
                modifier =
                    Modifier.height(50.dp)
            )


            Text(

                text =
                    "Change Password",

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,

                color =
                    Color.White
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            Text(

                text =
                    "Keep your SafePath AI account secure",

                color =
                    Color.Gray
            )


            Spacer(
                modifier =
                    Modifier.height(40.dp)
            )


            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            Color.White.copy(
                                alpha = 0.06f
                            )
                    )
            ) {


                Column(

                    modifier =
                        Modifier.padding(20.dp)
                ) {


                    GlassTextField(

                        value =
                            currentPassword,

                        onValueChange = {

                            currentPassword =
                                it

                            validationError =
                                null
                        },

                        label =
                            "Current Password",

                        visualTransformation =
                            PasswordVisualTransformation()
                    )


                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )


                    GlassTextField(

                        value =
                            newPassword,

                        onValueChange = {

                            newPassword =
                                it

                            validationError =
                                null
                        },

                        label =
                            "New Password",

                        visualTransformation =
                            PasswordVisualTransformation()
                    )


                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )


                    GlassTextField(

                        value =
                            confirmPassword,

                        onValueChange = {

                            confirmPassword =
                                it

                            validationError =
                                null
                        },

                        label =
                            "Confirm New Password",

                        visualTransformation =
                            PasswordVisualTransformation()
                    )


                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )


                    validationError?.let {

                        Text(

                            text =
                                it,

                            color =
                                Color.Red
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )
                    }


                    error?.let {

                        Text(

                            text =
                                it,

                            color =
                                Color.Red
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )
                    }


                    PrimaryButton(

                        text =

                            if (loading) {

                                "Changing..."

                            } else {

                                "Change Password"
                            },

                        onClick = {


                            when {


                                currentPassword
                                    .isBlank() -> {

                                    validationError =
                                        "Enter your current password"
                                }


                                newPassword
                                    .isBlank() -> {

                                    validationError =
                                        "Enter a new password"
                                }


                                newPassword.length < 8 -> {

                                    validationError =
                                        "New password must be at least 8 characters"
                                }


                                confirmPassword
                                    .isBlank() -> {

                                    validationError =
                                        "Confirm your new password"
                                }


                                newPassword !=
                                        confirmPassword -> {

                                    validationError =
                                        "New passwords do not match"
                                }


                                currentPassword ==
                                        newPassword -> {

                                    validationError =
                                        "New password must be different from current password"
                                }


                                else -> {


                                    validationError =
                                        null


                                    viewModel
                                        .changePassword(

                                            currentPassword =
                                                currentPassword,

                                            newPassword =
                                                newPassword
                                        )
                                }
                            }
                        }
                    )


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    TextButton(

                        modifier =
                            Modifier.fillMaxWidth(),

                        onClick = {

                            navController
                                .popBackStack()
                        }
                    ) {


                        Text(

                            text =
                                "Cancel",

                            color =
                                Color.Gray
                        )
                    }
                }
            }
        }
    }
}