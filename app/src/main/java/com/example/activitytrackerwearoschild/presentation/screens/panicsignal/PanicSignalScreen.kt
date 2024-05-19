package com.example.activitytrackerwearoschild.presentation.screens.panicsignal

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.compose.material3.IconButtonColors
import androidx.wear.compose.material3.Text
import com.example.activitytrackerwearoschild.R
import com.example.activitytrackerwearoschild.presentation.ScreenRoutes

@Composable
fun PanicSignalScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PanicSignalViewModel
) {
    val transition = remember {
        Animatable(0f)
    }
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = true) {
        transition.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 15000)
        ) {
            progress = value
        }
    }

    Box(
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            startAngle = 290f,
            endAngle = 250f,
            strokeWidth = 4.dp
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.sending_signal), color = Color.White)
            Spacer(Modifier.height(8.dp))
            IconButton(
                modifier = Modifier.size(48.dp),
                onClick = {
                    if (progress < 1f) {
                        navController.navigate(ScreenRoutes.Home.route)
                    }
                },
                colors = IconButtonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = "Cancel SOS"
                )
            }
        }

        if (progress == 1f) {
            Log.d("SOS", "Sent")
            navController.navigate(ScreenRoutes.Home.route)
        }
    }
}
