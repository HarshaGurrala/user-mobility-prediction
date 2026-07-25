package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.usermobilityprediction.app.navigation.Routes
import com.usermobilityprediction.app.ui.landing.AnimatedBackground
import com.usermobilityprediction.app.ui.landing.HeroContent
import com.usermobilityprediction.app.ui.landing.HeroVisual
import com.usermobilityprediction.app.ui.landing.LandingCTASection
import com.usermobilityprediction.app.ui.landing.LandingFeatureSection
import com.usermobilityprediction.app.ui.landing.LandingFooter

@Composable
fun LandingScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = 40.dp,
                    bottom = 30.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HeroVisual()

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            HeroContent(
                navController = navController
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            LandingFeatureSection()

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            LandingCTASection(
                onLoginClick = {
                    navController.navigate(Routes.LOGIN)
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                }
            )

            LandingFooter()

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}