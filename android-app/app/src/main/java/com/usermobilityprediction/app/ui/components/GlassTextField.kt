package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.ui.theme.GlassBorder
import com.usermobilityprediction.app.ui.theme.GlassWhite
import com.usermobilityprediction.app.ui.theme.PrimaryBlue
import com.usermobilityprediction.app.ui.theme.TextPrimary
import com.usermobilityprediction.app.ui.theme.TextSecondary

@Composable
fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = GlassWhite,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = GlassBorder,
                shape = RoundedCornerShape(18.dp)
            ),

        label = {
            Text(
                text = label,
                color = TextSecondary
            )
        },

        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,

        singleLine = singleLine,

        visualTransformation = visualTransformation,

        shape = RoundedCornerShape(18.dp),

        interactionSource = remember { MutableInteractionSource() },

        colors = OutlinedTextFieldDefaults.colors(

            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,

            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,

            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,

            cursorColor = PrimaryBlue,

            focusedLabelColor = PrimaryBlue,
            unfocusedLabelColor = TextSecondary
        )
    )
}