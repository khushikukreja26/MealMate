// ui/common/Shimmer.kt
package com.example.mealmate.ui.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.shimmerEffect(): Modifier {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val xAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer-x"
    )

    // Three-stop gradient “moving” left→right
    val brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF2A2A2A),
            androidx.compose.ui.graphics.Color(0xFF3A3A3A),
            androidx.compose.ui.graphics.Color(0xFF2A2A2A)
        ),
        start = Offset.Zero,
        end = Offset(xAnim.value * 1000f, xAnim.value * 1000f)
    )

    return this
        .clip(RoundedCornerShape(12.dp))
        .background(brush)
}
