package com.example.activitytrackerwearoschild.presentation.screens.panicsignal

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.compose.material3.IconButtonColors
import androidx.wear.compose.material3.ProgressIndicatorColors
import androidx.wear.compose.material3.Text
import com.example.activitytrackerwearoschild.R
import com.example.activitytrackerwearoschild.presentation.ScreenRoutes
import com.example.activitytrackerwearoschild.presentation.theme.CrimsonRed
import com.example.activitytrackerwearoschild.presentation.theme.VividRed
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.concurrent.TimeUnit

@Composable
fun PanicSignalScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PanicSignalViewModel,
    locationClient: FusedLocationProviderClient,
    context: Context
) {
    val transition = remember {
        Animatable(0f)
    }
    var progress by remember { mutableStateOf(0f) }

    val colorAnimation = rememberInfiniteTransition(label = "colorChangingAnimation")

    val color1 by colorAnimation.animateColor(
        initialValue = CrimsonRed,
        targetValue = VividRed,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "colorChanging1"
    )

    val color2 by colorAnimation.animateColor(
        initialValue = VividRed,
        targetValue = CrimsonRed,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "colorChanging2"
    )

    val gradientBrush = Brush.linearGradient(
        colors = listOf(color1, color2)
    )

    LaunchedEffect(key1 = true) {
        transition.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 15000)
        ) {
            progress = value
        }
    }

//    LaunchedEffect(Unit) {
//        //getCurrentLocation(locationClient, context)
//    }

    //getCurrentLocation(locationClient, context)

    Box(
        modifier.background(gradientBrush)
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            startAngle = 290f,
            endAngle = 250f,
            strokeWidth = 6.dp,
            colors = ProgressIndicatorColors(
                trackBrush = Brush.linearGradient(
                    listOf(
                        Color.White,
                        Color.White
                    )
                ),
                indicatorBrush = Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
            )
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                //modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.sending_signal),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape),
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
            viewModel.sendPanicSignal()
            navController.navigate(ScreenRoutes.Home.route)
        }
    }
}

fun getCurrentLocation(locationClient: FusedLocationProviderClient, context: Context) {
    val currentLocationRequest = CurrentLocationRequest.Builder()
        .setMaxUpdateAgeMillis(0)
        .setDurationMillis(TimeUnit.SECONDS.toMillis(1)) // Get a fresh location update
        .build()
    var cancellationTokenSource = CancellationTokenSource()
    //Log.d("location", "in getcurrent")

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    Log.d("location", "am permisiuni")
    locationClient.getCurrentLocation(currentLocationRequest, cancellationTokenSource.token)
        .addOnSuccessListener { location ->
            //Log.d("location", "aici????")
            if (location != null) {
                Log.d("location", "Lat: ${location.latitude}, Lng: ${location.longitude}")
            } else {
                Log.d("location", "Not found")
            }
        }
        .addOnFailureListener {
            it.message?.let { it1 -> Log.d("location", it1) }
        }
    //Log.d("dupa", "abc")
}
