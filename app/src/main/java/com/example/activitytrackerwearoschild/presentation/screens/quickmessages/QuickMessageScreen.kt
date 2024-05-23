package com.example.activitytrackerwearoschild.presentation.screens.quickmessages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

val emojiMessages = mapOf(
    "⛔" to "I'm busy!",
    "\uD83C\uDF92" to "Just left school",
    "\uD83D\uDCDE" to "Call me!",
    "\uD83E\uDD22" to "I'm feeling sick!",
    "\uD83E\uDD15" to "I'm hurt!",
    "\uD83C\uDF55" to "I'm hungry!",
    "\uD83C\uDFE0" to "I'm home!",
    "\uD83E\uDD38" to "Went outside to play"
)

@Composable
fun CircularLayout(
    modifier: Modifier = Modifier,
    viewModel: QuickMessageViewModel,
    items: List<@Composable () -> Unit>
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        val radiusPx = min(constraints.maxWidth, constraints.maxHeight) / 2.2f
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)//.offset{ IntOffset( x = centerX, y = centerY)}
        ) {
            Text(
                modifier = Modifier.wrapContentWidth().padding(4.dp),
                text = viewModel.messagePreview.value,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            SmallFloatingActionButton(
                onClick = { viewModel.sendQuickMessage(viewModel.messagePreview.value) },
                shape = CircleShape
            ) {
                Icon(
                    // Add icon here
                    Icons.Filled.Send,
                    modifier = Modifier.padding(end = 4.dp),
                    contentDescription = null,
                )
            }
        }

        Layout(
            content = {
                items.forEach { item ->
                    item()
                }
            }
        ) { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            val layoutWidth = constraints.maxWidth
            val layoutHeight = constraints.maxHeight
            val centerX = layoutWidth / 2
            val centerY = layoutHeight / 2

            val angleIncrement = 360.0 / placeables.size

            layout(layoutWidth, layoutHeight) {
                placeables.forEachIndexed { index, placeable ->
                    val angle = Math.toRadians(angleIncrement * index)
                    val x = (centerX + radiusPx * cos(angle) - placeable.width / 2).toInt()
                    val y = (centerY + radiusPx * sin(angle) - placeable.height / 2).toInt()
                    placeable.place(x, y)
                }
            }
        }
    }
}

@Composable
fun EmojiButton(emoji: String, onClick: () -> Unit) {
    val buttonColor = Color(0xFF8080FF).copy(alpha = 0.5f)

    Card(
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            modifier = Modifier.size(40.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            //modifier = Modifier.size(40.dp)//.padding(all = 8.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center, // Center the text
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = emoji, fontSize = 24.sp, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun QuickMessageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: QuickMessageViewModel
) {
    Box(
//        Modifier.background(
//            brush = Brush.linearGradient(
//                colors = listOf(
//                    Color(0xFF673AB7),
//                    Color(0xFF4CAF50),
//                    Color(0xFFFF9800),
//                    Color(0xFF2196F3)
//                ),
//                start = androidx.compose.ui.geometry.Offset(0f, 0f),
//                end = androidx.compose.ui.geometry.Offset(0f, 1000f)
//            )
//        )
    ) {
        CircularLayout(
            viewModel = viewModel,
            items = listOf(
                {
                    EmojiButton("⛔", {
                        emojiMessages.get("⛔")
                            ?.let { viewModel.updateMessagePreview(it) }
                    })
                }, // busy
                {
                    EmojiButton("\uD83C\uDF92", {
                        emojiMessages.get("\uD83C\uDF92")
                            ?.let { viewModel.updateMessagePreview(it) }
                    })
                }, // left school
                {
                    EmojiButton("\uD83D\uDCDE", {
                        emojiMessages.get("\uD83D\uDCDE")
                            ?.let { viewModel.updateMessagePreview(it) }
                    })
                }, //call me
                {
                    EmojiButton("\uD83E\uDD22", {
                        emojiMessages.get("\uD83E\uDD22")
                            ?.let { viewModel.updateMessagePreview(it) }
                    })
                }, // sick
                {
                    EmojiButton("\uD83E\uDD15", {
                        emojiMessages.get("\uD83E\uDD15")
                            ?.let { viewModel.updateMessagePreview(it) }
                    })
                }, // hurt
                {
                    EmojiButton("\uD83C\uDF55", {
                        emojiMessages.get("\uD83C\uDF55")
                            ?.let { viewModel.updateMessagePreview(it) }
                    })
                }, //hungry
                {
                    EmojiButton("\uD83C\uDFE0", {
                        emojiMessages.get("\uD83C\uDFE0")
                            ?.let { viewModel.updateMessagePreview(it) }
                    })
                }, // home
                {
                    EmojiButton("\uD83E\uDD38", {
                        emojiMessages.get("\uD83E\uDD38")
                            ?.let { viewModel.updateMessagePreview(it) }
                    })
                } // playing
            )
        )
    }

}
