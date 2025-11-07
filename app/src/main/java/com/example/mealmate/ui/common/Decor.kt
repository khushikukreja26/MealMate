package com.example.mealmate.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Fullscreen gradient using your Start screen palette */
@Composable
fun GradientBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.primary.copy(alpha = 0.95f),
                    MaterialTheme.colors.primary.copy(alpha = 0.75f),
                    MaterialTheme.colors.secondary.copy(alpha = 0.65f)
                )
            )
        )
    ) { content() }
}

/** Frosted glass card centered by default */
@Composable
fun FrostedCard(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = Color.White.copy(alpha = 0.08f),
        contentColor = Color.White,
        shape = RoundedCornerShape(24.dp),
        elevation = 0.dp
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            contentAlignment = contentAlignment
        ) { content() }
    }
}

/** Big rounded primary CTA that matches Start page style */
@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.clip(RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        ),
        elevation = ButtonDefaults.elevation(defaultElevation = 2.dp, pressedElevation = 6.dp)
    ) {
        Text(text, modifier = Modifier.padding(vertical = 6.dp))
    }
}