package com.example.activitytrackerwearoschild.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.activitytrackerwearoschild.data.DatabaseRepository
import com.example.activitytrackerwearoschild.data.DatastoreRepository
import com.example.activitytrackerwearoschild.presentation.screens.home.HomeScreen
import com.example.activitytrackerwearoschild.presentation.screens.home.HomeViewModel
import com.example.activitytrackerwearoschild.presentation.screens.pairing.PairingViewModel
import com.example.activitytrackerwearoschild.presentation.screens.pairing.PhonePairingScreen
import com.example.activitytrackerwearoschild.presentation.screens.panicsignal.PanicSignalScreen
import com.example.activitytrackerwearoschild.presentation.screens.panicsignal.PanicSignalViewModel
import com.example.activitytrackerwearoschild.presentation.screens.quickmessages.QuickMessageScreen
import com.example.activitytrackerwearoschild.presentation.screens.quickmessages.QuickMessageViewModel
import com.google.android.gms.location.FusedLocationProviderClient

object Graph {
    const val ROOT = "root_graph"
    const val PAIRING = "pairing_graph"
    const val HOME = "home_graph"
}

@Composable
fun TrackerApp(
    paired: Boolean,
    setUID: () -> Unit,
    datastoreRepository: DatastoreRepository,
    databaseRepository: DatabaseRepository,
    activity: ComponentActivity,
    locationClient: FusedLocationProviderClient
) {
    val navController = rememberNavController()
    val startDestination = if (paired) {  //Graph.AUTHENTICATION
        Graph.HOME
    } else {
        Graph.PAIRING
    }
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDestination
    ) {
        navigation(
            startDestination = ScreenRoutes.Pairing.route, route = Graph.PAIRING
        ) {
            composable(ScreenRoutes.Pairing.route) {
                PhonePairingScreen(
                    sendUID = setUID,
                    setLocationMonitor = { /*TODO*/ },
                    viewModel = PairingViewModel(datastoreRepository = datastoreRepository),
                    navController = navController,
                    activity = activity
                )
            }
        }

        navigation(
            startDestination = ScreenRoutes.Home.route, route = Graph.HOME
        ) {
            composable(ScreenRoutes.Home.route) {
                HomeScreen(
                    navController = navController,
                    viewModel = HomeViewModel(databaseRepository)
                )
            }

            composable(ScreenRoutes.QuickMessage.route) {
                QuickMessageScreen(
                    navController = navController,
                    viewModel = QuickMessageViewModel(databaseRepository)
                )
            }

            composable(ScreenRoutes.PanicSignal.route) {
                PanicSignalScreen(
                    navController = navController,
                    viewModel = PanicSignalViewModel(databaseRepository),
                    locationClient = locationClient,
                    context = activity
                )
            }
        }

    }
}