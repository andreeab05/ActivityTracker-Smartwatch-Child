package com.example.activitytrackerwearoschild.presentation
sealed class ScreenRoutes(val route: String) {
    object Pairing : ScreenRoutes("PAIRING")
    object Home : ScreenRoutes("HOME")
}
