package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.usermobilityprediction.app.ui.components.landing.HeroVisual
import com.usermobilityprediction.app.ui.components.landing.LandingFeatureSection
import com.usermobilityprediction.app.ui.components.landing.LandingCTASection
import com.usermobilityprediction.app.ui.components.landing.LandingHeader
import com.usermobilityprediction.app.ui.components.landing.LandingFooter
import androidx.navigation.NavController

@Composable
fun LandingScreen(
    navController: NavController? = null
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .verticalScroll(
                rememberScrollState()
            )
    ) {


        LandingHeader()

        Spacer(
            modifier = Modifier.height(45.dp)
        )


        Text(
            text = "AI powered mobility\n\nintelligence",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        Text(
            text = "AI powered mobility intelligence\nwith guardian safety protection.",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 60.dp)
        )


        Spacer(
            modifier = Modifier.height(100.dp)
        )


        HeroVisual()


        Spacer(
            modifier = Modifier.height(40.dp)
        )


        Text(
            text = "Smart Features",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        LandingFeatureSection()


        Spacer(
            modifier = Modifier.height(50.dp)
        )


        LandingCTASection(
            onLoginClick = {
                navController?.navigate("login")
            },
            onRegisterClick = {
                navController?.navigate("register")
            }
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        LandingFooter()


        Spacer(
            modifier = Modifier.height(40.dp)
        )
    }
}