package com.example.activitytrackerwearoschild.presentation.screens.home


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material3.IconButton
import com.example.activitytrackerwearoschild.R
import com.example.activitytrackerwearoschild.presentation.ScreenRoutes
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF4A90E2), Color(0xFFFF6F61))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val firstRowVisible = remember { mutableStateOf(false) }
        val secondRowVisible = remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            // Start the first row animation
            firstRowVisible.value = true
            // Wait for the first row animation to complete
            delay(1000) // Duration of the first row animation
            // Start the second row animation
            secondRowVisible.value = true
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = firstRowVisible.value,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight }, // Slide in from the top
                    animationSpec = tween(durationMillis = 1000) // Animation duration
                )
            ) {
                Row {
                    Text(
                        text = stringResource(id = R.string.greeting),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        //modifier = Modifier.weight(1f),
                        color = Color.White
                    )
                    WavingHandAnimation()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(
                visible = secondRowVisible.value,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight }, // Slide in from the top
                    animationSpec = tween(durationMillis = 1000) // Animation duration
                )
            ) {
                Column() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.quick_message),
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Button(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(32.dp, 32.dp),
                            onClick = { navController.navigate(ScreenRoutes.QuickMessage.route) },
                        ) {
                            Icon(imageVector = Icons.Filled.Email, contentDescription = null)
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.panic_signal),
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Button(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(32.dp, 32.dp),
                            onClick = { navController.navigate(ScreenRoutes.PanicSignal.route) },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ReportProblem,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WavingHandAnimation() {
    val rotation = remember { Animatable(0f) }
    val animationSpec = tween<Float>(
        durationMillis = 500,
        easing = LinearEasing// CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f), // Adjust easing as needed
    )

//    val animatedRotation by animateFloatAsState(
//        targetValue = rotation.value,
//        animationSpec = animationSpec, label = "rotatoion"
//    )

    LaunchedEffect(Unit) {
        while (true) {
            for (i in 0..1) {
                rotation.animateTo(
                    targetValue = 15f,
                    animationSpec = animationSpec
                )
                rotation.animateTo(
                    targetValue = -15f,
                    animationSpec = animationSpec
                )
            }
            delay(1000)
        }
    }
    val animatedRotation by rotation.asState()
    Text(text = "\uD83D\uDC4B", fontSize = 24.sp, modifier = Modifier.rotate(animatedRotation))

}
