package com.example.activitytrackerwearoschild.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NotListedLocation
import androidx.compose.material.icons.automirrored.filled.SendToMobile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun PhonePairingScreen(
    modifier: Modifier = Modifier,
    sendUID: () -> Unit,
    setLocationMonitor: () -> Unit,
    //medicationReminders: List<MedicationReminderState>
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Connect to phone app")
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { sendUID() },
            ) {
                Icon(Icons.AutoMirrored.Filled.SendToMobile, contentDescription = null)
            }
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { }//setLocationMonitor()},
            ) {
                Icon(Icons.AutoMirrored.Filled.NotListedLocation, contentDescription = null)
            }
        }
    }
}