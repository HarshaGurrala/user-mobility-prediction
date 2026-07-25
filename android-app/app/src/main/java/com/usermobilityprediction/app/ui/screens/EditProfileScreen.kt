package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.usermobilityprediction.app.ui.components.GlassTextField
import com.usermobilityprediction.app.ui.components.PrimaryButton
import com.usermobilityprediction.app.viewmodel.ProfileViewModel


@Composable
fun EditProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {

    val user by profileViewModel.user.collectAsState()

    val loading by profileViewModel.loading.collectAsState()

    val updateLoading by
    profileViewModel.updateLoading.collectAsState()

    val updateSuccess by
    profileViewModel.updateSuccess.collectAsState()

    val updateError by
    profileViewModel.updateError.collectAsState()


    var fullName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var phoneNumber by remember {
        mutableStateOf("")
    }


    // Load current user when screen opens

    LaunchedEffect(Unit) {

        profileViewModel.loadCurrentUser()

    }


    // Populate fields after API response

    LaunchedEffect(user) {

        user?.let {

            fullName = it.full_name

            email = it.email

            phoneNumber =
                it.phone_number ?: ""
        }
    }


    // Navigate back after successful update

    LaunchedEffect(updateSuccess) {

        if (updateSuccess) {

            navController.popBackStack()

            profileViewModel.resetUpdateState()
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
                    Modifier.height(40.dp)
            )


            Text(
                text = "Edit Profile",
                style =
                    MaterialTheme.typography.headlineMedium,
                color = Color.White
            )


            Text(
                text =
                    "Update your personal information",
                color = Color.Gray
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


                    // Loading current profile

                    if (loading) {

                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 20.dp
                                    ),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            CircularProgressIndicator(
                                color =
                                    Color(0xFF3B82F6)
                            )
                        }
                    }


                    GlassTextField(
                        value = fullName,

                        onValueChange = {
                            fullName = it
                        },

                        label = "Full Name"
                    )


                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )


                    GlassTextField(
                        value = email,

                        onValueChange = {
                            email = it
                        },

                        label = "Email"
                    )


                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )


                    GlassTextField(
                        value = phoneNumber,

                        onValueChange = {
                            phoneNumber = it
                        },

                        label = "Phone Number"
                    )


                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )


                    // Update error

                    updateError?.let {

                        Text(
                            text = it,
                            color = Color.Red
                        )

                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )
                    }


                    // Save button

                    PrimaryButton(

                        text =
                            if (updateLoading) {
                                "Saving..."
                            } else {
                                "Save Changes"
                            },

                        onClick = {

                            if (
                                fullName
                                    .trim()
                                    .isBlank()
                            ) {

                                return@PrimaryButton
                            }


                            if (
                                email
                                    .trim()
                                    .isBlank()
                            ) {

                                return@PrimaryButton
                            }


                            profileViewModel
                                .updateCurrentUser(

                                    fullName =
                                        fullName,

                                    email =
                                        email,

                                    phoneNumber =
                                        phoneNumber
                                )
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
                            text = "Cancel",
                            color = Color.Gray
                        )
                    }
                }
            }


            Spacer(
                modifier =
                    Modifier.height(30.dp)
            )
        }
    }
}