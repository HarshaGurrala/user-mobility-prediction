
package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.ui.components.AlertAnalyticsCard
import com.usermobilityprediction.app.ui.components.AnalyticsError
import com.usermobilityprediction.app.ui.components.AnalyticsHeader
import com.usermobilityprediction.app.ui.components.AnalyticsLoading
import com.usermobilityprediction.app.ui.components.DailyDistanceSection
import com.usermobilityprediction.app.ui.components.OverviewSummaryCard
import com.usermobilityprediction.app.ui.components.PredictionAnalyticsCard
import com.usermobilityprediction.app.ui.components.SafeZoneAnalyticsCard
import com.usermobilityprediction.app.ui.components.SafetyAnalyticsCard
import com.usermobilityprediction.app.ui.components.WeeklyDistanceSection
import com.usermobilityprediction.app.viewmodel.AnalyticsViewModel
import com.usermobilityprediction.app.viewmodel.UserDashboardViewModel
import com.usermobilityprediction.app.ui.components.PredictionCard
import com.usermobilityprediction.app.viewmodel.PredictionViewModel
import com.usermobilityprediction.app.ui.components.DashboardPredictionCard
import android.util.Log
@Composable
fun AnalyticsScreen(
    navController: NavController,
    userId: Int
){

    val predictionViewModel: PredictionViewModel = viewModel()

    val predictionState by predictionViewModel.uiState.collectAsState()

    /*
     * ============================================================
     * VIEW MODELS
     * ============================================================
     */

    val dashboardViewModel: UserDashboardViewModel = viewModel()
    val dashboardState by dashboardViewModel.uiState.collectAsState()

    val viewModel: AnalyticsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()


    /*
     * ============================================================
     * LOAD ANALYTICS
     *
     * Uses the dynamically supplied logged-in user ID.
     *
     * IMPORTANT:
     * No hardcoded user ID.
     * Existing Analytics API flow is unchanged.
     * ============================================================
     */

    LaunchedEffect(userId) {
        dashboardViewModel.refreshDashboard(userId)



        viewModel.loadAnalytics(userId)

        predictionViewModel.loadPrediction(userId)

    }


    /*
     * ============================================================
     * LOADING STATE
     * ============================================================
     */

    if (uiState.loading) {

        AnalyticsLoading()

        return

    }


    /*
     * ============================================================
     * ERROR STATE
     * ============================================================
     */

    uiState.error?.let { errorMessage ->

        AnalyticsError(errorMessage)

        return

    }


    /*
     * ============================================================
     * REAL BACKEND OVERVIEW DATA
     * ============================================================
     */

    val overview = uiState.overview


    /*
     * ============================================================
     * MAIN ANALYTICS PAGE
     * ============================================================
     */

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF050505)
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),

        verticalArrangement =
            Arrangement.spacedBy(12.dp)

    ) {


        /*
         * ========================================================
         * ANALYTICS HEADER
         *
         * Uses real dashboard and analytics backend values.
         * ========================================================
         */

        AnalyticsHeader(
            userName = dashboardState.userName ?: "User",
            lastUpdated = dashboardState.lastUpdated ?: "Unknown",
            safetyStatus = overview?.currentSafetyStatus ?: "Unknown",
            currentLocation = overview?.currentLocation ?: "Unknown"
        )


        /*
         * ========================================================
         * CURRENT LOCATION
         *
         * FULL WIDTH
         * ========================================================
         */

        OverviewSummaryCard(

            modifier =
                Modifier.fillMaxWidth(),

            title =
                "Current Location",

            value =
                overview?.currentLocation
                    ?: "Unknown",

            description =
                "Current location",

            icon =
                Icons.Default.LocationOn

        )


        DashboardPredictionCard(

            location =
                dashboardState.predictedLocation,

            confidence =
                dashboardState.predictionConfidence,

            status =
                dashboardState.predictionStatus

        )


        /*
         * ========================================================
         * SAFETY STATUS
         *
         * FULL WIDTH
         * ========================================================
         */

        OverviewSummaryCard(

            modifier =
                Modifier.fillMaxWidth(),

            title =
                "Safety Status",

            value =
                overview?.currentSafetyStatus
                    ?: "Unknown",

            description =
                "Current safety status",

            icon =
                Icons.Default.Notifications

        )


        /*
         * ========================================================
         * LOCATIONS + DISTANCE
         *
         * TWO COLUMNS
         * ========================================================
         */

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            OverviewSummaryCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Locations",

                value =
                    "${overview?.totalLocations ?: 0}",

                description =
                    "Tracked locations",

                icon =
                    Icons.Default.LocationOn

            )


            OverviewSummaryCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Distance",

                value =
                    String.format(

                        "%.1f km",

                        overview?.totalDistance
                            ?: 0.0

                    ),

                description =
                    "Total travelled",

                icon =
                    Icons.Default.Home

            )

        }


        /*
         * ========================================================
         * PREDICTIONS + ALERTS
         *
         * TWO COLUMNS
         * ========================================================
         */

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            OverviewSummaryCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Predictions",

                value =
                    "${overview?.totalPredictions ?: 0}",

                description =
                    "AI predictions",

                icon =
                    Icons.Default.ShowChart

            )


            OverviewSummaryCard(

                modifier =
                    Modifier.weight(1f),

                title =
                    "Alerts",

                value =
                    "${overview?.totalAlerts ?: 0}",

                description =
                    "Safety alerts",

                icon =
                    Icons.Default.Notifications

            )

        }


        /*
         * ========================================================
         * DAILY TRAVEL
         *
         * Real backend data.
         * ========================================================
         */

        DailyDistanceSection(

            dailyDistance =
                uiState.dailyDistance

        )


        /*
         * ========================================================
         * WEEKLY TRAVEL
         *
         * Real backend data.
         * ========================================================
         */

        WeeklyDistanceSection(

            weeklyDistance =
                uiState.weeklyDistance

        )


        /*
         * ========================================================
         * AI PREDICTION ANALYTICS
         *
         * Real backend data.
         * ========================================================
         */

        PredictionAnalyticsCard(

            prediction =
                uiState.prediction

        )

        PredictionCard(
            prediction =
                predictionState.latestPrediction
        )




        /*
         * ========================================================
         * SAFETY ANALYTICS
         *
         * Real backend data.
         * ========================================================
         */

        SafetyAnalyticsCard(

            safety =
                uiState.safety

        )


        /*
         * ========================================================
         * ALERT ANALYTICS
         *
         * Real backend data.
         * ========================================================
         */

        AlertAnalyticsCard(

            alerts =
                uiState.alerts

        )


        /*
         * ========================================================
         * SAFE ZONE ANALYTICS
         *
         * Real backend data.
         *
         * The SafeZoneAnalyticsCard intentionally displays
         * only location names and radius.
         *
         * Latitude and longitude are NOT displayed.
         * ========================================================
         */

        SafeZoneAnalyticsCard(

            safeZone =
                uiState.safeZones

        )


        /*
         * ========================================================
         * BOTTOM SPACING
         * ========================================================
         */

        Spacer(

            modifier =
                Modifier.height(30.dp)

        )

    }

}

