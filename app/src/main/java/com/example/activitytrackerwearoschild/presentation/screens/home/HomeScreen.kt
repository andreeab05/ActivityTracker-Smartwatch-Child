package com.example.activitytrackerwearoschild.presentation.screens.home


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column {
        Text(text = "Home")
        Log.d("Home", "we're home!!")
    }
}