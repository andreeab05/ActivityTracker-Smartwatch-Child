package com.example.activitytrackerwearoschild.presentation

sealed class ScreenRoutes(val route: String) {
    object Pairing : ScreenRoutes("PAIRING")
    object Home : ScreenRoutes("HOME")
    object QuickMessage : ScreenRoutes("QUICK_MESSAGE")
    object PanicSignal : ScreenRoutes("PANIC_SIGNAL")
}
