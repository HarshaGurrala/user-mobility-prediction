package com.usermobilityprediction.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    GlassTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,

        visualTransformation =
            if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),

        trailingIcon = {

            IconButton(
                onClick = {
                    passwordVisible = !passwordVisible
                }
            ) {

                Icon(
                    imageVector =
                        if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,

                    contentDescription = "Toggle Password"
                )
            }
        }
    )
}